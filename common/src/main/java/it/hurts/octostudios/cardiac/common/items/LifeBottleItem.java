package it.hurts.octostudios.cardiac.common.items;

import it.hurts.octostudios.cardiac.common.entities.ThrownLifeBottle;
import it.hurts.octostudios.cardiac.common.init.EntityRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class LifeBottleItem extends Item implements ProjectileItem {
    public LifeBottleItem() {
        super(new Item.Properties().arch$tab(CreativeModeTabs.INGREDIENTS).rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide) {
            ThrownLifeBottle bottle = new ThrownLifeBottle(EntityRegistry.THROWN_LIFE_BOTTLE.get(), level);

            bottle.setItem(stack);
            bottle.setOwner(player);
            bottle.setPos(player.getEyePosition());
            bottle.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.7F, 1.0F);

            level.addFreshEntity(bottle);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        if (!player.getAbilities().instabuild)
            stack.shrink(1);

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ThrownLifeBottle thrownLifeBottle = new ThrownLifeBottle(EntityRegistry.THROWN_LIFE_BOTTLE.get(), level);

        thrownLifeBottle.setPos(position.x(), position.y(), position.z());
        thrownLifeBottle.setItem(itemStack);

        return thrownLifeBottle;
    }
}