package it.hurts.octostudios.cardiac.common.init;

import net.minecraft.world.level.block.DispenserBlock;

public class DispenserBehaviorRegistry {
    public static void registerCommon() {
        DispenserBlock.registerProjectileBehavior(ItemRegistry.LIFE_BOTTLE.get());
    }
}