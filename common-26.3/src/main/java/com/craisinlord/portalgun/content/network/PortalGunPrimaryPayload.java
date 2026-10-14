package com.craisinlord.portalgun.content.network;

import com.craisinlord.portalgun.PortalGun;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PortalGunPrimaryPayload(boolean offhand) implements CustomPacketPayload {
    public static final Type<PortalGunPrimaryPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(PortalGun.MODID, "portal_gun_primary"));
    public static final StreamCodec<ByteBuf, PortalGunPrimaryPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, PortalGunPrimaryPayload::offhand,
            PortalGunPrimaryPayload::new
    );

    @Override
    public Type<PortalGunPrimaryPayload> type() {
        return TYPE;
    }
}
