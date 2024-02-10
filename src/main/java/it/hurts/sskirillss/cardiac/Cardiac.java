package it.hurts.sskirillss.cardiac;

import it.hurts.sskirillss.cardiac.config.CardiacConfig;
import it.hurts.sskirillss.cardiac.init.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cardiac.MODID)
public class Cardiac {
    public static final String MODID = "cardiac";

    public Cardiac() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CardiacConfig.GENERAL, MODID + ".toml");

        MinecraftForge.EVENT_BUS.register(this);

        EnchantmentRegistry.register();
        EntityRegistry.register();
        SoundRegistry.register();
        ItemRegistry.register();
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        DispenserBehaviorRegistry.register();
    }
}