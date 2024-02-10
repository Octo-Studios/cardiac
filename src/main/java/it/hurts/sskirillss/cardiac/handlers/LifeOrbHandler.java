package it.hurts.sskirillss.cardiac.handlers;

import it.hurts.sskirillss.cardiac.config.CardiacConfig;
import it.hurts.sskirillss.cardiac.entities.LifeOrb;
import it.hurts.sskirillss.cardiac.init.EnchantmentRegistry;
import it.hurts.sskirillss.cardiac.init.EntityRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class LifeOrbHandler {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (CardiacConfig.SHOULD_BE_KILLED_BY_PLAYER.get() && !(event.getSource().getEntity() instanceof Player))
            return;

        LivingEntity target = event.getEntityLiving();
        Level level = target.getCommandSenderWorld();

        Random random = target.getRandom();

        float percentage = (float) (CardiacConfig.GENERAL_PERCENTAGE.get() + (event.getSource().getEntity() instanceof Player player
                ? EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.LIFESTEAL.get(), player.getMainHandItem()) * CardiacConfig.LIFESTEAL_PERCENTAGE.get() : 0F));

        float maxHealth = target.getMaxHealth();
        float toDrop = maxHealth * percentage;

        if (toDrop == 0)
            return;

        int steps = CardiacConfig.LIFE_ORBS_MIN_AMOUNT.get() + random.nextInt((int) Math.ceil(toDrop));

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