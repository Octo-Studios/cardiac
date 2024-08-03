package it.hurts.octostudios.cardiac.fabric;

import it.hurts.octostudios.cardiac.common.Cardiac;
import net.fabricmc.api.ModInitializer;

public final class CardiacFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Cardiac.init();
    }
}
