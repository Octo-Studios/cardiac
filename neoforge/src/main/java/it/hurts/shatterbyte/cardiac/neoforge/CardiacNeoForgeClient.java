package it.hurts.shatterbyte.cardiac.neoforge;

import it.hurts.shatterbyte.cardiac.common.client.renderer.entity.LifeOrbRenderer;
import it.hurts.shatterbyte.cardiac.common.init.EntityRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class CardiacNeoForgeClient {
    public CardiacNeoForgeClient(IEventBus modBus) {
        modBus.addListener(CardiacNeoForgeClient::onRegisterRenderers);
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.THROWN_LIFE_BOTTLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityRegistry.LIFE_ORB.get(), LifeOrbRenderer::new);
    }
}
