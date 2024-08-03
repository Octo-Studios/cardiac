package it.hurts.octostudios.cardiac.fabric;

import it.hurts.octostudios.cardiac.common.CardiacClient;
import net.fabricmc.api.ClientModInitializer;

public final class CardiacFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CardiacClient.init();
    }
}
