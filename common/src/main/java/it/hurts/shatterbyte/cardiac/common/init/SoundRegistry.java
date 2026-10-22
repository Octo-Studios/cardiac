package it.hurts.shatterbyte.cardiac.common.init;

import it.hurts.shatterbyte.cardiac.common.registry.DeferredRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SoundRegistry {
    public static final DeferredRegistry<SoundEvent> SOUNDS = DeferredRegistry.create(Registries.SOUND_EVENT);

    public static final Supplier<SoundEvent> LIFE_ORB_PICKUP = SOUNDS.register("life_orb_pickup", key -> SoundEvent.createVariableRangeEvent(key.identifier()));
}
