package com.craisinlord.portalgun.forge.client;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.client.PortalGunAnthologyClientHooks;
import com.craisinlord.portalgun.content.client.PortalGunCrosshairRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunProjectileRenderer;
import com.craisinlord.portalgun.content.network.PortalGunNetwork;
import com.craisinlord.portalgun.forge.PortalGunForgeContent;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PortalGun.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class PortalGunForgeClientSetup {
    public static final KeyMapping ZOOM = new KeyMapping("key.portal_gun.zoom", KeyConflictContext.IN_GAME, InputConstants.Type.MOUSE,
            InputConstants.MOUSE_BUTTON_MIDDLE, "key.categories.portal_gun");
    public static final KeyMapping RESET = new KeyMapping("key.portal_gun.reset", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
            InputConstants.KEY_R, "key.categories.portal_gun");

    private PortalGunForgeClientSetup() {
    }

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(ZOOM);
        event.register(RESET);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PortalGunForgeContent.PORTAL_ENTITY.get(), PortalGunPortalRenderer::new);
        event.registerEntityRenderer(PortalGunForgeContent.PROJECTILE_ENTITY.get(), PortalGunProjectileRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        PortalGunNetwork.registerClient();
        PortalGunAnthologyClientHooks.register();
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "portal_gun_crosshair",
                (gui, graphics, partialTick, screenWidth, screenHeight) -> PortalGunCrosshairRenderer.render(graphics));
    }
}
