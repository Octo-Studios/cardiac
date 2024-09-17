package it.hurts.octostudios.cardiac.common;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LootEvent;
import it.hurts.octostudios.cardiac.common.entities.LifeOrb;
import it.hurts.octostudios.cardiac.common.init.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.regex.PatternSyntaxException;

import static it.hurts.octostudios.cardiac.common.init.ConfigRegistry.CONFIG;

public class Cardiac {
    public static final String MODID = "cardiac";

    public static void init() {
        EnchantmentRegistry.registerCommon();
        EntityRegistry.registerCommon();
        SoundRegistry.registerCommon();
        ItemRegistry.registerCommon();

        EntityEvent.LIVING_DEATH.register((LivingEntity entity, DamageSource source) -> {
            var killer = source.getEntity();

            if (!(killer instanceof Player) && CONFIG.isShouldBeKilledByPlayer())
                return EventResult.pass();

            var level = entity.getCommandSenderWorld();

            var random = entity.getRandom();

            var basePercentage = 0D;

            var entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();

            for (var entry : CONFIG.getGeneralPercentages().entrySet()) {
                var pattern = entry.getKey();
                var percentage = entry.getValue();

                try {
                    if (entityId.matches(pattern))
                        basePercentage = percentage;
                } catch (PatternSyntaxException exception) {
                    if (entityId.equals(pattern))
                        basePercentage = percentage;
                }
            }

            if (basePercentage == 0D)
                return EventResult.pass();

            var lifestealPercentage = 0D;

            if (killer instanceof LivingEntity livingEntity) {
                int enchantment = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.LIFESTEAL.get(), livingEntity.getMainHandItem());

                if (enchantment > 0) {
                    for (var entry : CONFIG.getLifestealPercentages().entrySet()) {
                        var pattern = entry.getKey();
                        var percentage = entry.getValue();

                        try {
                            if (entityId.matches(pattern))
                                lifestealPercentage = percentage;
                        } catch (PatternSyntaxException exception) {
                            if (entityId.equals(pattern))
                                lifestealPercentage = percentage;
                        }
                    }
                }
            }

            var toDrop = entity.getMaxHealth() * (basePercentage + lifestealPercentage);

            if (toDrop == 0)
                return EventResult.pass();

            var steps = CONFIG.getMinOrbsAmount() + random.nextInt((int) Math.ceil(toDrop));

            for (int i = 0; i < steps; i++) {
                var orb = new LifeOrb(EntityRegistry.LIFE_ORB.get(), level);

                orb.setLife((float) (toDrop / steps));
                orb.setPos(entity.position().add(0, entity.getBbHeight() / 2F, 0));
                orb.setDeltaMovement(
                        (-1 + 2 * random.nextFloat()) * 0.15F,
                        0.1F + random.nextFloat() * 0.3F,
                        (-1 + 2 * random.nextFloat()) * 0.15F
                );

                level.addFreshEntity(orb);
            }

            return EventResult.pass();
        });

        LootEvent.MODIFY_LOOT_TABLE.register((@Nullable LootDataManager lootDataManager, ResourceLocation id, LootEvent.LootTableModificationContext context, boolean builtin) -> {
            if (id.getPath().matches(".*chests.*"))
                context.addPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.LIFE_BOTTLE.get()).setWeight(5).when(LootItemRandomChanceCondition.randomChance(0.2F)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))));
        });

        LifecycleEvent.SETUP.register(() -> {
            DispenserBehaviorRegistry.registerCommon();
            ConfigRegistry.registerCommon();
        });
    }
}