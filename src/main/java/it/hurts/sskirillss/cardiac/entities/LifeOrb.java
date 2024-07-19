package it.hurts.sskirillss.cardiac.entities;

import it.hurts.sskirillss.cardiac.config.CardiacConfig;
import it.hurts.sskirillss.cardiac.init.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;

public class LifeOrb extends Entity {
    private static final EntityDataAccessor<Float> LIFE = SynchedEntityData.defineId(LifeOrb.class, EntityDataSerializers.FLOAT);

    public LifeOrb(EntityType<? extends LifeOrb> type, Level level) {
        super(type, level);
    }

    public float getMaxLife() {
        return CardiacConfig.LIFE_ORB_MAX_HEALTH.get().floatValue();
    }

    public float getLife() {
        return this.getEntityData().get(LIFE);
    }

    public void setLife(float life) {
        this.getEntityData().set(LIFE, life);
    }

    public int getStage() {
        int stages = CardiacConfig.LIFE_ORB_MAX_STAGES.get();

        return Mth.clamp(getLife() <= 1 ? 1 : (int) Math.ceil(Math.min((getLife() / (getMaxLife() / Math.max(1, stages - 1))) + 1, stages)), 1, stages);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isNoGravity())
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));

        if (!this.level().noCollision(this.getBoundingBox()))
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getZ());

        if (this.tickCount >= 20 && this.getLife() < getMaxLife()) {
            for (LifeOrb orb : this.level().getEntitiesOfClass(LifeOrb.class, this.getBoundingBox().inflate(0.25F))) {
                if (orb.getUUID().equals(this.getUUID()) || orb.isRemoved() || orb.getLife() >= getMaxLife())
                    continue;

                float diff = getMaxLife() - this.getLife();

                if (orb.getLife() < diff) {
                    this.setLife(this.getLife() + orb.getLife());

                    orb.discard();
                } else {
                    orb.setLife(orb.getLife() - diff);

                    this.setLife(getMaxLife());
                }
            }
        }

        double maxDistance = CardiacConfig.LIFE_ORB_FOLLOW_DISTANCE.get();

        Player player = this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), maxDistance, entity -> {
            Player entry = (Player) entity;

            return !entry.isSpectator() && entry.getHealth() < entry.getMaxHealth();
        });

        if (player != null) {
            this.setDeltaMovement(this.getDeltaMovement().add(player.position().add(0F, player.getBbHeight() / 2F, 0F).subtract(this.position()).normalize().scale((maxDistance - this.position().distanceTo(player.position())) / (maxDistance * 8))));

            if (this.position().distanceTo(player.position()) <= player.getBbWidth() && player.getHealth() < player.getMaxHealth()) {
                float diff = player.getMaxHealth() - player.getHealth();

                if (this.getLife() > diff) {
                    this.setLife(this.getLife() - diff);

                    player.heal(diff);
                } else {
                    player.heal(this.getLife());

                    this.discard();
                }

                this.level().playSound(null, this.blockPosition(), SoundRegistry.LIFE_ORB_PICKUP.get(), SoundSource.MASTER, 0.5F, 1.25F + this.level().getRandom().nextFloat() * 0.75F);
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());

        float friction = 0.98F;

        if (this.onGround()) {
            BlockPos pos = getBlockPosBelowThatAffectsMyMovement();

            friction = this.level().getBlockState(pos).getFriction(this.level(), pos, this) * 0.98F;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(friction, 0.98D, friction));

        if (this.onGround())
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));

        if (this.tickCount >= CardiacConfig.LIFE_ORB_LIFETIME.get() * 20)
            this.discard();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        setLife(tag.getFloat("life"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("life", getLife());
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(LIFE, 1F);
    }

    @Override
    protected BlockPos getBlockPosBelowThatAffectsMyMovement() {
        return this.getOnPos(0.999F);
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.AMBIENT;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (LIFE.equals(pKey))
            this.refreshDimensions();

        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        float scale = 0.075F + (getStage() * 0.035F);

        return EntityDimensions.scalable(scale, scale);
    }
}