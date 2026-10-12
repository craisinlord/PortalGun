package com.craisinlord.portalgun.fabric;

import com.craisinlord.portalgun.fabric.client.PortalGunClientHandler;
import com.craisinlord.portalgun.fabric.client.PortalGunKeyBindings;
import com.craisinlord.portalgun.fabric.network.PortalGunFabricNetworking;
import com.craisinlord.portalgun.registry.PortalGunContent;
import com.craisinlord.portalgun.content.client.PortalGunRollClientState;
import com.craisinlord.portalgun.content.client.PortalGunCrosshairRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunProjectileRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalViewRenderer;
import com.craisinlord.portalgun.PortalGun;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.craisinlord.portalgun.fabric.client.SeparateLargeItemModels;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public final class PortalGunFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CoreShaderRegistrationCallback.EVENT.register(context -> {
            PortalGunPortalViewRenderer.clearPortalViewShader();
            try {
                context.register(ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, "portal_view"),
                        DefaultVertexFormat.POSITION_TEX, PortalGunPortalViewRenderer::setPortalViewShader);
            } catch (java.io.IOException | RuntimeException exception) {
                PortalGun.LOGGER.error("Failed to register portal view shader", exception);
            }
        });
        PortalGunKeyBindings.register();
        com.craisinlord.portalgun.fabric.network.PortalGunFabricClientNetworking.register();
        ClientTickEvents.END_CLIENT_TICK.register(PortalGunRollClientState::tick);
        PortalGunClientHandler.register();
        ModelLoadingPlugin.register(plugin -> plugin.addModels(SeparateLargeItemModels.all()));
        EntityRendererRegistry.register(PortalGunContent.PORTAL_ENTITY, PortalGunPortalRenderer::new);
        EntityRendererRegistry.register(PortalGunContent.PROJECTILE_ENTITY, PortalGunProjectileRenderer::new);
        HudRenderCallback.EVENT.register((graphics, tickCounter) -> PortalGunCrosshairRenderer.render(graphics));
        WorldRenderEvents.LAST.register(context -> PortalGunPortalViewRenderer.render(
                context.camera(), context.positionMatrix(), context.projectionMatrix(), context.tickCounter()));
    }
}
