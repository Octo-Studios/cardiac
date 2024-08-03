package it.hurts.octostudios.cardiac.neoforge;

import it.hurts.octostudios.cardiac.common.Cardiac;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Cardiac.MODID)
public final class CardiacNeoForge {
    public CardiacNeoForge(IEventBus modBus) {
        Cardiac.init();

        if (FMLEnvironment.dist == Dist.CLIENT)
            new CardiacNeoForgeClient(modBus);
    }
}
