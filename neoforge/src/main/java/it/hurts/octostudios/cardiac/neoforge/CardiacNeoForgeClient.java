package it.hurts.octostudios.cardiac.neoforge;

import it.hurts.octostudios.cardiac.common.CardiacClient;
import net.neoforged.bus.api.IEventBus;

public final class CardiacNeoForgeClient {
    public CardiacNeoForgeClient(IEventBus modBus) {
        CardiacClient.init();
    }
}
