package it.hurts.sskirillss.cardiac.handlers;

import it.hurts.sskirillss.cardiac.entities.LifeOrb;
import it.hurts.sskirillss.cardiac.init.EntityRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LifeOrbHandler {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();

        RandomSource random = target.getRandom();

        float percentage = 0.15F;

        float maxHealth = target.getMaxHealth();
        float toDrop = maxHealth * percentage;

        if (toDrop == 0)
            return;

        int steps = 2 + random.nextInt((int) Math.ceil(toDrop));

        for (int i = 0; i < steps; i++) {
            LifeOrb orb = new LifeOrb(EntityRegistry.LIFE_ORB.get(), level);

            orb.setLife(toDrop / steps);
            orb.setPos(target.position().add(0, target.getBbHeight() / 2F, 0));
            orb.setDeltaMovement(
                    (-1 + 2 * random.nextFloat()) * 0.15F,
                    0.1F + random.nextFloat() * 0.3F,
                    (-1 + 2 * random.nextFloat()) * 0.15F
            );

            level.addFreshEntity(orb);
        }
    }
}