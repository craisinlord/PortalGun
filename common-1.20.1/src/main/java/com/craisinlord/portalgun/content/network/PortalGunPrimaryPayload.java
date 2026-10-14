package com.craisinlord.portalgun.content.network;

import com.craisinlord.portalgun.PortalGun;
import io.netty.buffer.ByteBuf;
import com.craisinlord.anthology.network.ByteBufCodecs;
import com.craisinlord.anthology.network.StreamCodec;
import com.craisinlord.anthology.network.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PortalGunPrimaryPayload(boolean offhand) implements CustomPacketPayload {
    public static final Type<PortalGunPrimaryPayload> TYPE = new Type<>(new ResourceLocation(PortalGun.MODID, "portal_gun_primary"));
    public static final StreamCodec<ByteBuf, PortalGunPrimaryPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, PortalGunPrimaryPayload::offhand,
            PortalGunPrimaryPayload::new
    );

    @Override
    public Type<PortalGunPrimaryPayload> type() {
        return TYPE;
    }
}
