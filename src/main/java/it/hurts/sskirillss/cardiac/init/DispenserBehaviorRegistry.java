package it.hurts.sskirillss.cardiac.init;

import net.minecraft.world.level.block.DispenserBlock;

public class DispenserBehaviorRegistry {
    public static void register() {
        DispenserBlock.registerProjectileBehavior(ItemRegistry.LIFE_BOTTLE.get());
    }
}