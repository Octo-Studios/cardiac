package it.hurts.octostudios.cardiac.quilt;

import it.hurts.octostudios.cardiac.common.Cardiac;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public final class CardiacQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        Cardiac.init();
    }
}