package com.craisinlord.portalgun.fabric;

import com.craisinlord.portalgun.content.client.PortalGunAnthologyClientHooks;
import com.craisinlord.portalgun.content.client.PortalGunCrosshairRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunProjectileRenderer;
import com.craisinlord.portalgun.content.network.PortalGunNetwork;
import com.craisinlord.portalgun.fabric.client.PortalGunClientHandler;
import com.craisinlord.portalgun.fabric.client.PortalGunFabricItemRenderers;
import com.craisinlord.portalgun.fabric.client.PortalGunKeyBindings;
import com.craisinlord.portalgun.fabric.client.SeparateLargeItemModels;
import com.craisinlord.portalgun.registry.PortalGunContent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public final class PortalGunFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PortalGunFabricItemRenderers.register();
        PortalGunKeyBindings.register();
        PortalGunNetwork.registerClient();
        PortalGunAnthologyClientHooks.register();
        PortalGunClientHandler.register();
        ModelLoadingPlugin.register(plugin -> plugin.addModels(SeparateLargeItemModels.all()));
        EntityRendererRegistry.register(PortalGunContent.PORTAL_ENTITY, PortalGunPortalRenderer::new);
        EntityRendererRegistry.register(PortalGunContent.PROJECTILE_ENTITY, PortalGunProjectileRenderer::new);
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> PortalGunCrosshairRenderer.render(graphics));
    }
}
