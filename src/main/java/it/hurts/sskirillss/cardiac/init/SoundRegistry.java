package it.hurts.sskirillss.cardiac.init;

import it.hurts.sskirillss.cardiac.Cardiac;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Cardiac.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> LIFE_ORB_PICKUP = SOUNDS.register("life_orb_pickup", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Cardiac.MODID, "life_orb_pickup")));

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}