package it.hurts.sskirillss.cardiac;

import it.hurts.sskirillss.cardiac.init.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cardiac.MODID)
public class Cardiac {
    public static final String MODID = "cardiac";

    public Cardiac() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);

        MinecraftForge.EVENT_BUS.register(this);

        EnchantmentRegistry.register();
        EntityRegistry.register();
        SoundRegistry.register();
        CodecRegistry.register();
        ItemRegistry.register();
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        DispenserBehaviorRegistry.register();
    }
}