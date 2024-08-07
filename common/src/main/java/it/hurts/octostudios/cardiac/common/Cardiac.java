package it.hurts.octostudios.cardiac.common;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LootEvent;
import it.hurts.octostudios.cardiac.common.entities.LifeOrb;
import it.hurts.octostudios.cardiac.common.init.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import static it.hurts.octostudios.cardiac.common.init.ConfigRegistry.CONFIG;

public class Cardiac {
    public static final String MODID = "cardiac";

    public static void init() {
        EntityRegistry.registerCommon();
        SoundRegistry.registerCommon();
        ItemRegistry.registerCommon();

        EntityEvent.LIVING_DEATH.register((LivingEntity entity, DamageSource source) -> {
            Entity killer = source.getEntity();

            if (!(killer instanceof Player) && CONFIG.isShouldBeKilledByPlayer())
                return EventResult.pass();

            Level level = entity.getCommandSenderWorld();

            RandomSource random = entity.getRandom();

            float percentage = (float) (CONFIG.getGeneralPercentage() + (killer instanceof LivingEntity livingEntity ? livingEntity.getMainHandItem().getOrDefault(DataComponents.ENCHANTMENTS,
                    ItemEnchantments.EMPTY).getLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentRegistry.LIFESTEAL)) * CONFIG.getLifestealPercentage() : 0F));

            float maxHealth = entity.getMaxHealth();
            float toDrop = maxHealth * percentage;

            if (toDrop == 0)
                return EventResult.pass();

            int steps = CONFIG.getMinOrbsAmount() + random.nextInt((int) Math.ceil(toDrop));

            for (int i = 0; i < steps; i++) {
                LifeOrb orb = new LifeOrb(EntityRegistry.LIFE_ORB.get(), level);

                orb.setLife(toDrop / steps);
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

        LootEvent.MODIFY_LOOT_TABLE.register((ResourceKey<LootTable> key, LootEvent.LootTableModificationContext context, boolean builtin) -> {
            if (key.location().getPath().matches(".*chests.*"))
                context.addPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.LIFE_BOTTLE.get()).setWeight(5).when(LootItemRandomChanceCondition.randomChance(0.2F)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))));
        });

        LifecycleEvent.SETUP.register(() -> {
            DispenserBehaviorRegistry.registerCommon();
            ConfigRegistry.registerCommon();
        });
    }
}