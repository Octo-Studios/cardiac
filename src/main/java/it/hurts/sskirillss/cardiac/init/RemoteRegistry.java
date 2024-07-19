package it.hurts.sskirillss.cardiac.init;

import it.hurts.sskirillss.cardiac.Cardiac;
import it.hurts.sskirillss.cardiac.client.renderer.entity.LifeOrbRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Cardiac.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RemoteRegistry {
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.LIFE_ORB.get(), LifeOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.THROWN_LIFE_BOTTLE.get(), ThrownItemRenderer::new);
    }
}