package it.hurts.shatterbyte.cardiac.common.entities;

import it.hurts.shatterbyte.cardiac.common.init.EntityRegistry;
import it.hurts.shatterbyte.cardiac.common.init.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownLifeBottle extends ThrowableItemProjectile {
    public ThrownLifeBottle(EntityType<? extends ThrownLifeBottle> type, Level level) {
        super(type, level);
    }

    public ThrownLifeBottle(Level level, LivingEntity owner, ItemStack stack) {
        super(EntityRegistry.THROWN_LIFE_BOTTLE.get(), owner, level, stack);
    }

    public ThrownLifeBottle(Level level, double x, double y, double z, ItemStack stack) {
        super(EntityRegistry.THROWN_LIFE_BOTTLE.get(), x, y, z, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.LIFE_BOTTLE.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.07D;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (this.level() instanceof ServerLevel level) {
            level.levelEvent(2002, this.blockPosition(), new PotionContents(Potions.HEALING).getColor());

            var steps = 5 + random.nextInt(10);

            for (var i = 0; i < steps; i++) {
                var orb = new LifeOrb(EntityRegistry.LIFE_ORB.get(), level);

                orb.setLife((1F + (random.nextFloat() * 3F)));
                orb.setPos(Vec3.atCenterOf(this.blockPosition()));
                orb.setDeltaMovement(
                        (-1 + 2 * random.nextFloat()) * 0.15F,
                        0.1F + random.nextFloat() * 0.2F,
                        (-1 + 2 * random.nextFloat()) * 0.15F
                );

                level.addFreshEntity(orb);
            }

            this.discard();
        }
    }
}