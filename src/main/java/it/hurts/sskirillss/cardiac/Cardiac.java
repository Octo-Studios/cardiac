package it.hurts.sskirillss.cardiac;

import it.hurts.sskirillss.cardiac.config.CardiacConfig;
import it.hurts.sskirillss.cardiac.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Cardiac.MODID)
public class Cardiac {
    public static final String MODID = "cardiac";

    public Cardiac(IEventBus bus, ModContainer container) {
        bus.addListener(this::setupCommon);

        container.registerConfig(ModConfig.Type.COMMON, CardiacConfig.GENERAL);

        EntityRegistry.register(bus);
        SoundRegistry.register(bus);
        LootCodecRegistry.register(bus);
        ItemRegistry.register(bus);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        DispenserBehaviorRegistry.register();
    }
}