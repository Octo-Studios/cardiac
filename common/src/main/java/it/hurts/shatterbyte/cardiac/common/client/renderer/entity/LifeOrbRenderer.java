package it.hurts.shatterbyte.cardiac.common.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.hurts.shatterbyte.cardiac.common.Cardiac;
import it.hurts.shatterbyte.cardiac.common.client.renderer.entity.state.LifeOrbRenderState;
import it.hurts.shatterbyte.cardiac.common.entities.LifeOrb;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;

public class LifeOrbRenderer extends EntityRenderer<LifeOrb, LifeOrbRenderState> {
    public LifeOrbRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.shadowRadius = 0F;
        this.shadowStrength = 0.75F;
    }

    @Override
    protected int getBlockLightLevel(LifeOrb entity, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(entity, pos) + 7, 0, 15);
    }

    @Override
    protected float getShadowRadius(LifeOrbRenderState state) {
        return 0.035F + (state.stage * 0.025F);
    }

    @Override
    public void submit(LifeOrbRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();

        var scale = (float) (0.4F + Math.sin(state.ageInTicks * 0.1F) * 0.05F);

        poseStack.scale(scale, scale, scale);

        poseStack.translate(0.0F, 0.1F + (state.stage * 0.05F), 0.0F);

        poseStack.mulPose(camera.orientation);

        var alpha = (int) Math.min(255, 255 * (0.75F + Math.sin(state.ageInTicks * 0.25F) * 0.1F));

        collector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucentCullItemTarget(getTextureLocation(state.stage)), (pose, consumer) -> {
            vertex(consumer, pose, -0.5F, -0.5F, alpha, 0, 1);
            vertex(consumer, pose, 0.5F, -0.5F, alpha, 1, 1);
            vertex(consumer, pose, 0.5F, 0.5F, alpha, 1, 0);
            vertex(consumer, pose, -0.5F, 0.5F, alpha, 0, 0);
        });

        poseStack.popPose();

        super.submit(state, poseStack, collector, camera);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, int alpha, float u, float v) {
        consumer.addVertex(pose, x, y, 0F).setColor(255, 255, 255, alpha).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightCoordsUtil.FULL_BRIGHT).setNormal(pose, 0F, 1F, 0F);
    }

    private static Identifier getTextureLocation(int stage) {
        return Identifier.fromNamespaceAndPath(Cardiac.MODID, "textures/entity/life_orb_" + stage + ".png");
    }

    @Override
    public LifeOrbRenderState createRenderState() {
        return new LifeOrbRenderState();
    }

    @Override
    public void extractRenderState(LifeOrb entity, LifeOrbRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.stage = entity.getStage();
    }
}
