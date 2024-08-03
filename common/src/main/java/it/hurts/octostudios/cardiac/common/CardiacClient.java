package it.hurts.octostudios.cardiac.common;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import it.hurts.octostudios.cardiac.common.client.renderer.entity.LifeOrbRenderer;
import it.hurts.octostudios.cardiac.common.init.EntityRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class CardiacClient {
    public static void init() {
        EntityRendererRegistry.register(EntityRegistry.THROWN_LIFE_BOTTLE, ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LIFE_ORB, LifeOrbRenderer::new);
    }
}