package com.craisinlord.portalgun.fabric.client;

import com.craisinlord.portalgun.content.client.PortalGunResetClientState;
import com.craisinlord.portalgun.content.client.PortalGunZoomClientState;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.network.PortalGunPrimaryPayload;
import com.craisinlord.portalgun.content.network.PortalGunMoonAimPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

public final class PortalGunClientHandler {
    private static boolean lastAttackDown;
    private PortalGunClientHandler() { }
    public static void register() { ClientTickEvents.END_CLIENT_TICK.register(PortalGunClientHandler::tick); }
    private static void tick(Minecraft mc) {
        if (mc.player == null) PortalGunResetClientState.clear();
        boolean hasGun = mc.player != null && (mc.player.getMainHandItem().getItem() instanceof PortalGunItem || mc.player.getOffhandItem().getItem() instanceof PortalGunItem);
        boolean input = mc.gui.screen() == null;
        PortalGunZoomClientState.tick(mc, hasGun && input, PortalGunKeyBindings.consumeZoom() && input);
        boolean reset = PortalGunResetClientState.tick(mc, hasGun && input, PortalGunKeyBindings.resetDown(), ClientPlayNetworking::send);
        if (mc.player == null || mc.level == null || !input) { lastAttackDown = false; return; }
        boolean offhand = !(mc.player.getMainHandItem().getItem() instanceof PortalGunItem) && mc.player.getOffhandItem().getItem() instanceof PortalGunItem;
        if (!offhand && !(mc.player.getMainHandItem().getItem() instanceof PortalGunItem)) { lastAttackDown = false; return; }
        boolean attack = mc.options.keyAttack.isDown();
        if (attack && !lastAttackDown && !reset) {
            ClientPlayNetworking.send(new PortalGunMoonAimPayload(false, false));
            ClientPlayNetworking.send(new PortalGunPrimaryPayload(offhand));
        }
        lastAttackDown = attack;
    }
}
