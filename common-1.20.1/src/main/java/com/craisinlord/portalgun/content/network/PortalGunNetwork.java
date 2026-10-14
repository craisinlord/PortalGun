package com.craisinlord.portalgun.content.network;

import com.craisinlord.anthology.network.AnthologyNetwork;
import com.craisinlord.portalgun.content.client.PortalGunIndicatorClientState;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.portalgun.PortalGunIndicatorSync;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import com.craisinlord.portalgun.content.portalgun.PortalGunResetManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public final class PortalGunNetwork {
    private static boolean registered;

    private PortalGunNetwork() {
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }
        registered = true;
        AnthologyNetwork.registerServerbound(PortalGunPrimaryPayload.TYPE, PortalGunPrimaryPayload.STREAM_CODEC, PortalGunNetwork::handlePrimary);
        AnthologyNetwork.registerServerbound(PortalGunIndicatorRequestPayload.TYPE, PortalGunIndicatorRequestPayload.STREAM_CODEC,
                (payload, player) -> PortalGunIndicatorSync.handleRequest(player, payload));
        AnthologyNetwork.registerServerbound(PortalGunResetPayload.TYPE, PortalGunResetPayload.STREAM_CODEC,
                (payload, player) -> PortalGunResetManager.handleInput(player, payload.action()));
        AnthologyNetwork.registerServerbound(PortalGunMoonAimPayload.TYPE, PortalGunMoonAimPayload.STREAM_CODEC,
                (payload, player) -> PortalGunMoonAimSync.handle(player, payload));
        AnthologyNetwork.registerClientbound(PortalGunIndicatorPayload.TYPE, PortalGunIndicatorPayload.STREAM_CODEC,
                (payload, player) -> PortalGunIndicatorClientState.update(payload));
        PortalGunIndicatorSync.setSender(AnthologyNetwork::sendToPlayer);
    }

    public static void registerClient() {
        PortalGunIndicatorClientState.setRequestSender(AnthologyNetwork::sendToServer);
        PortalGunMoonAimSync.setClientReporter(() -> AnthologyNetwork.sendToServer(new PortalGunMoonAimPayload(false, false)));
    }

    private static void handlePrimary(PortalGunPrimaryPayload payload, ServerPlayer player) {
        ItemStack stack = payload.offhand() ? player.getOffhandItem() : player.getMainHandItem();
        if (stack.getItem() instanceof PortalGunItem item) {
            item.firePrimary(player.serverLevel(), player, stack, payload.offhand());
        }
    }
}
