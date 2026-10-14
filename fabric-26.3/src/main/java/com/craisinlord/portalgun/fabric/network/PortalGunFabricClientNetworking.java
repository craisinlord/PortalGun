package com.craisinlord.portalgun.fabric.network;

import com.craisinlord.portalgun.content.client.PortalGunIndicatorClientState;
import com.craisinlord.portalgun.content.network.PortalGunIndicatorPayload;
import com.craisinlord.portalgun.content.network.PortalGunMoonAimPayload;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class PortalGunFabricClientNetworking {
    private PortalGunFabricClientNetworking() { }
    public static void register() {
        PortalGunIndicatorClientState.setRequestSender(ClientPlayNetworking::send);
        PortalGunMoonAimSync.setClientReporter(() -> ClientPlayNetworking.send(new PortalGunMoonAimPayload(false, false)));
        ClientPlayNetworking.registerGlobalReceiver(PortalGunIndicatorPayload.TYPE,
                (payload, context) -> context.client().execute(() -> PortalGunIndicatorClientState.update(payload)));
    }
}
