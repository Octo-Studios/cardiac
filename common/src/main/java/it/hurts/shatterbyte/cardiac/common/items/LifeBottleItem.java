package it.hurts.shatterbyte.cardiac.common.items;

import it.hurts.shatterbyte.cardiac.common.entities.ThrownLifeBottle;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class LifeBottleItem extends Item implements ProjectileItem {
    public LifeBottleItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (level instanceof ServerLevel serverLevel)
            Projectile.spawnProjectileFromRotation(ThrownLifeBottle::new, serverLevel, stack, player, -20.0F, 0.7F, 1.0F);

        player.awardStat(Stats.ITEM_USED.get(this));

        stack.consume(1, player);

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new ThrownLifeBottle(level, position.x(), position.y(), position.z(), itemStack);
    }
}
