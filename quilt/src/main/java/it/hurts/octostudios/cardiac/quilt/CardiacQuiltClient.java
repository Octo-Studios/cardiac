package it.hurts.octostudios.cardiac.quilt;

import it.hurts.octostudios.cardiac.common.CardiacClient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public final class CardiacQuiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        CardiacClient.init();
    }
}