package com.craisinlord.portalgun.neoforge;

import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.network.*;
import com.craisinlord.portalgun.content.portalgun.*;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class PortalGunNetworking {
    private PortalGunNetworking() { }
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToServer(PortalGunPrimaryPayload.TYPE, PortalGunPrimaryPayload.STREAM_CODEC, (payload, ctx) -> ctx.enqueueWork(() -> {
            if (!(ctx.player() instanceof ServerPlayer player)) return;
            var stack = payload.offhand() ? player.getOffhandItem() : player.getMainHandItem();
            if (stack.getItem() instanceof PortalGunItem item) item.firePrimary(player.level(), player, stack, payload.offhand());
        })).playToServer(PortalGunIndicatorRequestPayload.TYPE, PortalGunIndicatorRequestPayload.STREAM_CODEC, (payload, ctx) -> ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer player) PortalGunIndicatorSync.handleRequest(player, payload);
        })).playToServer(PortalGunResetPayload.TYPE, PortalGunResetPayload.STREAM_CODEC, (payload, ctx) -> ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer player) PortalGunResetManager.handleInput(player, payload.action());
        })).playToServer(PortalGunMoonAimPayload.TYPE, PortalGunMoonAimPayload.STREAM_CODEC, (payload, ctx) -> ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer player) PortalGunMoonAimSync.handle(player, payload);
        })).playToClient(PortalGunIndicatorPayload.TYPE, PortalGunIndicatorPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> com.craisinlord.portalgun.content.client.PortalGunIndicatorClientState.update(payload)));
        PortalGunIndicatorSync.setSender(PacketDistributor::sendToPlayer);
    }
}
