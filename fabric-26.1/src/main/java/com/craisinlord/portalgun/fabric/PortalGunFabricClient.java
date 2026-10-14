package com.craisinlord.portalgun.fabric;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.client.PortalGunAnthologyClientHooks;
import com.craisinlord.portalgun.content.client.PortalGunCrosshairRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunProjectileRenderer;
import com.craisinlord.portalgun.fabric.client.PortalGunClientHandler;
import com.craisinlord.portalgun.fabric.client.PortalGunKeyBindings;
import com.craisinlord.portalgun.fabric.network.PortalGunFabricClientNetworking;
import com.craisinlord.portalgun.registry.PortalGunContent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public final class PortalGunFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PortalGunKeyBindings.register();
        PortalGunFabricClientNetworking.register();
        PortalGunAnthologyClientHooks.register();
        PortalGunClientHandler.register();
        EntityRendererRegistry.register(PortalGunContent.PORTAL_ENTITY, PortalGunPortalRenderer::new);
        EntityRendererRegistry.register(PortalGunContent.PROJECTILE_ENTITY, PortalGunProjectileRenderer::new);
        HudElementRegistry.attachElementAfter(VanillaHudElements.CROSSHAIR, Identifier.fromNamespaceAndPath(PortalGun.MODID, "portal_gun_crosshair"),
                (graphics, deltaTracker) -> PortalGunCrosshairRenderer.render(graphics));
    }
}
