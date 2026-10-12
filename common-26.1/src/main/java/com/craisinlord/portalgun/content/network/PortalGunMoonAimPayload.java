package com.craisinlord.portalgun.content.network;

import com.craisinlord.portalgun.PortalGun;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PortalGunMoonAimPayload(boolean tracked, boolean aiming) implements CustomPacketPayload {
    public static final Type<PortalGunMoonAimPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(PortalGun.MODID, "portal_gun_moon_aim"));
    public static final StreamCodec<ByteBuf, PortalGunMoonAimPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, PortalGunMoonAimPayload::tracked,
            ByteBufCodecs.BOOL, PortalGunMoonAimPayload::aiming,
            PortalGunMoonAimPayload::new
    );

    @Override
    public Type<PortalGunMoonAimPayload> type() {
        return TYPE;
    }
}
