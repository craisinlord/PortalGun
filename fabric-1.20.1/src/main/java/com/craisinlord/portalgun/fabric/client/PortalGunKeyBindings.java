package com.craisinlord.portalgun.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class PortalGunKeyBindings {
    private static final String CATEGORY = "key.categories.portal_gun";
    private static final KeyMapping ZOOM = new KeyMapping("key.portal_gun.zoom", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY);
    private static final KeyMapping RESET = new KeyMapping("key.portal_gun.reset", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY);

    private PortalGunKeyBindings() {
    }

    public static void register() {
        KeyBindingHelper.registerKeyBinding(ZOOM);
        KeyBindingHelper.registerKeyBinding(RESET);
    }

    public static boolean consumeZoom() {
        return ZOOM.consumeClick();
    }

    public static boolean resetDown() {
        return RESET.isDown() || RESET.consumeClick();
    }
}
