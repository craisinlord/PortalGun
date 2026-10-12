package com.craisinlord.portalgun.fabric.network;

import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.network.PortalGunIndicatorPayload;
import com.craisinlord.portalgun.content.network.PortalGunIndicatorRequestPayload;
import com.craisinlord.portalgun.content.network.PortalGunMoonAimPayload;
import com.craisinlord.portalgun.content.network.PortalGunPrimaryPayload;
import com.craisinlord.portalgun.content.network.PortalGunResetPayload;
import com.craisinlord.portalgun.content.network.PortalGunTransitPayload;
import com.craisinlord.portalgun.content.portalgun.PortalGunIndicatorSync;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import com.craisinlord.portalgun.content.portalgun.PortalGunResetManager;
import com.craisinlord.portalgun.content.portalgun.PortalGunTransitHandler;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class PortalGunFabricNetworking {
    private PortalGunFabricNetworking() { }

    public static void register() {
        PayloadTypeRegistry.playS2C().register(PortalGunIndicatorPayload.TYPE, PortalGunIndicatorPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PortalGunPrimaryPayload.TYPE, PortalGunPrimaryPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PortalGunTransitPayload.TYPE, PortalGunTransitPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PortalGunIndicatorRequestPayload.TYPE, PortalGunIndicatorRequestPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PortalGunResetPayload.TYPE, PortalGunResetPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PortalGunMoonAimPayload.TYPE, PortalGunMoonAimPayload.STREAM_CODEC);
        PortalGunIndicatorSync.setSender(ServerPlayNetworking::send);
        ServerPlayNetworking.registerGlobalReceiver(PortalGunPrimaryPayload.TYPE, (payload, context) -> context.server().execute(() -> {
            var player = context.player();
            var stack = payload.offhand() ? player.getOffhandItem() : player.getMainHandItem();
            if (stack.getItem() instanceof PortalGunItem item) item.firePrimary(player.serverLevel(), player, stack, payload.offhand());
        }));
        ServerPlayNetworking.registerGlobalReceiver(PortalGunTransitPayload.TYPE, (payload, context) -> {
            if (context.server().isSameThread()) {
                PortalGunTransitHandler.handle(context.player(), payload);
            } else {
                context.server().execute(() -> PortalGunTransitHandler.handle(context.player(), payload));
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(PortalGunIndicatorRequestPayload.TYPE, (payload, context) -> context.server().execute(() -> PortalGunIndicatorSync.handleRequest(context.player(), payload)));
        ServerPlayNetworking.registerGlobalReceiver(PortalGunResetPayload.TYPE, (payload, context) -> context.server().execute(() -> PortalGunResetManager.handleInput(context.player(), payload.action())));
        ServerPlayNetworking.registerGlobalReceiver(PortalGunMoonAimPayload.TYPE, (payload, context) -> context.server().execute(() -> PortalGunMoonAimSync.handle(context.player(), payload)));
    }

}
