package com.craisinlord.portalgun.content.client.renderer;

import com.craisinlord.portalgun.config.PortalGunSettings;
import com.craisinlord.portalgun.content.client.model.PortalGunPortalModel;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.GraphicsPreset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class PortalGunPortalRenderer extends GeoEntityRenderer<PortalGunPortalEntity, EntityRenderState> {
    public static final DataTicket<PortalVisual> PORTAL_VISUAL = DataTicket.create("portal_gun_portal_visual", PortalVisual.class);

    public PortalGunPortalRenderer(EntityRendererProvider.Context context) {
        super(context, new PortalGunPortalModel());
        this.shadowRadius = 0.0F;
    }

    @Override
    protected boolean affectedByCulling(PortalGunPortalEntity entity) {
        return false;
    }

    @Override
    public void extractRenderState(PortalGunPortalEntity entity, EntityRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        boolean fancy = isFancyPortalsEnabled();
        float frameV = Math.floorMod((int) ((entity.level().getGameTime() + partialTick) / 5.0F), 4) * 0.25F;
        ((GeoRenderState) renderState).addGeckolibData(PORTAL_VISUAL, new PortalVisual(
                entity.getWidthVec().normalize(),
                entity.getUpVec().normalize(),
                entity.getNormalVec().normalize(),
                entity.getNormalVec(),
                entity.getPortalVisualScale(partialTick),
                entity.getPortalWidth() * 0.5F,
                entity.getPortalHeight() * 0.5F,
                entity.getPortalColor(),
                fancy,
                frameV,
                portalTexture(entity, fancy),
                PortalGunPortalRim.texture(entity)));
    }

    @Override
    public void submit(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraState) {
        PortalVisual visual = ((GeoRenderState) renderState).getGeckolibData(PORTAL_VISUAL);
        if (visual == null) {
            return;
        }
        poseStack.pushPose();
        float halfWidth = visual.halfWidth();
        float halfHeight = visual.halfHeight();
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(visual.texture()), (pose, consumer) -> {
            Matrix4f matrix = pose.pose();
            if (visual.fancy()) {
                putAnimatedPortalVertex(consumer, matrix, visual, -halfWidth, -halfHeight, 0.0F, 1.0F);
                putAnimatedPortalVertex(consumer, matrix, visual, -halfWidth, halfHeight, 0.0F, 0.0F);
                putAnimatedPortalVertex(consumer, matrix, visual, halfWidth, halfHeight, 1.0F, 0.0F);
                putAnimatedPortalVertex(consumer, matrix, visual, halfWidth, -halfHeight, 1.0F, 1.0F);
            } else {
                putPortalVertex(consumer, matrix, visual, -halfWidth, -halfHeight, 0.0F, 1.0F, true);
                putPortalVertex(consumer, matrix, visual, -halfWidth, halfHeight, 0.0F, 0.0F, true);
                putPortalVertex(consumer, matrix, visual, halfWidth, halfHeight, 1.0F, 0.0F, true);
                putPortalVertex(consumer, matrix, visual, halfWidth, -halfHeight, 1.0F, 1.0F, true);
            }
        });
        float rimWidth = halfWidth + PortalGunPortalRim.EXTRA_WIDTH;
        float rimHeight = halfHeight + PortalGunPortalRim.EXTRA_HEIGHT;
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(visual.rimTexture()), (pose, edge) -> {
            Matrix4f matrix = pose.pose();
            putPortalVertex(edge, matrix, visual, -rimWidth, -rimHeight, 0.0F, 1.0F, true);
            putPortalVertex(edge, matrix, visual, -rimWidth, rimHeight, 0.0F, 0.0F, true);
            putPortalVertex(edge, matrix, visual, rimWidth, rimHeight, 1.0F, 0.0F, true);
            putPortalVertex(edge, matrix, visual, rimWidth, -rimHeight, 1.0F, 1.0F, true);
        });
        poseStack.popPose();
    }

    private static Identifier portalTexture(PortalGunPortalEntity entity, boolean fancy) {
        return fancy
                ? entity.getVariant().vortexTexture(entity.getPortalSide())
                : PortalGunPortalRim.texture(entity);
    }

    private static boolean isFancyPortalsEnabled() {
        Minecraft minecraft = Minecraft.getInstance();
        GraphicsPreset preset = minecraft.options.graphicsPreset().get();
        return PortalGunSettings.portalGunFancyPortals()
                && preset != GraphicsPreset.FAST
                && !minecraft.options.improvedTransparency().get();
    }

    private static Vec3 vertexPosition(PortalVisual visual, float horizontal, float vertical) {
        return visual.widthDirection().scale(horizontal * visual.scale())
                .add(visual.upDirection().scale(vertical * visual.scale()))
                .add(visual.normalDirection().scale(0.03125D));
    }

    private static void putAnimatedPortalVertex(VertexConsumer consumer, Matrix4f pose, PortalVisual visual, float horizontal, float vertical, float u, float v) {
        Vec3 position = vertexPosition(visual, horizontal, vertical);
        consumer.addVertex(pose, (float) position.x, (float) position.y, (float) position.z)
                .setColor(255, 255, 255, 210)
                .setUv(u, visual.frameV() + v * 0.25F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightCoordsUtil.FULL_BRIGHT)
                .setNormal((float) visual.normal().x, (float) visual.normal().y, (float) visual.normal().z);
    }

    private static void putPortalVertex(VertexConsumer consumer, Matrix4f pose, PortalVisual visual, float horizontal, float vertical, float u, float v, boolean channelTint) {
        Vec3 position = vertexPosition(visual, horizontal, vertical);
        int color = visual.color();
        int red = channelTint ? color >> 16 & 0xFF : 255;
        int green = channelTint ? color >> 8 & 0xFF : 255;
        int blue = channelTint ? color & 0xFF : 255;
        int alpha = channelTint ? PortalGunPortalRim.ENTITY_ALPHA : 210;
        consumer.addVertex(pose, (float) position.x, (float) position.y, (float) position.z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightCoordsUtil.FULL_BRIGHT)
                .setNormal((float) visual.normal().x, (float) visual.normal().y, (float) visual.normal().z);
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void applyRotations(RenderPassInfo renderPassInfo, PoseStack poseStack, float nativeScale) {
    }

    @Override
    public RenderType getRenderType(EntityRenderState renderState, Identifier texture) {
        PortalVisual visual = ((GeoRenderState) renderState).getGeckolibData(PORTAL_VISUAL);
        return RenderTypes.entityTranslucent(visual == null ? texture : visual.texture());
    }

    public record PortalVisual(
            Vec3 widthDirection,
            Vec3 upDirection,
            Vec3 normalDirection,
            Vec3 normal,
            float scale,
            float halfWidth,
            float halfHeight,
            int color,
            boolean fancy,
            float frameV,
            Identifier texture,
            Identifier rimTexture
    ) {
    }
}
