package it.hurts.shatterbyte.cardiac.fabric;

import it.hurts.shatterbyte.cardiac.common.client.renderer.entity.LifeOrbRenderer;
import it.hurts.shatterbyte.cardiac.common.init.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class CardiacFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register(EntityRegistry.THROWN_LIFE_BOTTLE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(EntityRegistry.LIFE_ORB.get(), LifeOrbRenderer::new);
    }
}
