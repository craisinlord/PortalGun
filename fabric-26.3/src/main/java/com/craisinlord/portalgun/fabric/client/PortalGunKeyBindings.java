package com.craisinlord.portalgun.fabric.client;

import com.craisinlord.portalgun.PortalGun;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public final class PortalGunKeyBindings {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(PortalGun.MODID, "portal_gun"));
    private static final KeyMapping ZOOM = new KeyMapping("key.portal_gun.zoom", InputConstants.Type.KEYBOARD, InputConstants.KEY_Z, CATEGORY);
    private static final KeyMapping RESET = new KeyMapping("key.portal_gun.reset", InputConstants.Type.KEYBOARD, InputConstants.KEY_R, CATEGORY);

    private PortalGunKeyBindings() {
    }

    public static void register() {
        KeyMappingHelper.registerKeyMapping(ZOOM);
        KeyMappingHelper.registerKeyMapping(RESET);
    }

    public static boolean consumeZoom() {
        return ZOOM.consumeClick();
    }

    public static boolean resetDown() {
        return RESET.isDown() || RESET.consumeClick();
    }
}
