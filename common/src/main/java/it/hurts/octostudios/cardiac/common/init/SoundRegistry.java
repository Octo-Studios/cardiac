package it.hurts.octostudios.cardiac.common.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.hurts.octostudios.cardiac.common.Cardiac;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Cardiac.MODID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> LIFE_ORB_PICKUP = SOUNDS.register("life_orb_pickup", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Cardiac.MODID, "life_orb_pickup")));

    public static void registerCommon() {
        SOUNDS.register();
    }
}