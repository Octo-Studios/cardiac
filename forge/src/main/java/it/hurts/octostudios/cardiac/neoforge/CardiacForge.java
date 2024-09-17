package it.hurts.octostudios.cardiac.neoforge;

import dev.architectury.platform.forge.EventBuses;
import it.hurts.octostudios.cardiac.common.Cardiac;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Cardiac.MODID)
public final class CardiacForge {
    public CardiacForge() {
        EventBuses.registerModEventBus(Cardiac.MODID, FMLJavaModLoadingContext.get().getModEventBus());

        Cardiac.init();

        if (FMLEnvironment.dist == Dist.CLIENT)
            new CardiacForgeClient();
    }
}