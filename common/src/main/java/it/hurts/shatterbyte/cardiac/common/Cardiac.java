package it.hurts.shatterbyte.cardiac.common;

import it.hurts.shatterbyte.cardiac.common.entities.LifeOrb;
import it.hurts.shatterbyte.cardiac.common.init.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Consumer;
import java.util.regex.PatternSyntaxException;

import static it.hurts.shatterbyte.cardiac.common.init.ConfigRegistry.CONFIG;

public class Cardiac {
    public static final String MODID = "cardiac";

    public static void init() {
        ConfigRegistry.registerCommon();
    }

    public static void setup() {
        DispenserBlock.registerProjectileBehavior(ItemRegistry.LIFE_BOTTLE.get());
    }

    public static void onLivingDeath(LivingEntity entity, DamageSource source) {
        var killer = source.getEntity();

        if (!(killer instanceof Player) && CONFIG.isShouldBeKilledByPlayer())
            return;

        var level = entity.level();

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

        var lifestealPercentage = 0D;

        if (killer instanceof LivingEntity livingEntity) {
            var enchantment = livingEntity.getMainHandItem().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(EnchantmentRegistry.LIFESTEAL));

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
            return;

        var steps = CONFIG.getMinOrbsAmount() + random.nextInt((int) Math.ceil(toDrop));

        for (var i = 0; i < steps; i++) {
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
    }

    public static void onModifyLootTable(ResourceKey<LootTable> key, Consumer<LootPool.Builder> consumer) {
        if (key.identifier().getPath().matches(".*chests.*"))
            consumer.accept(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.LIFE_BOTTLE.get()).setWeight(5).when(LootItemRandomChanceCondition.randomChance(0.2F)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))));
    }
}
