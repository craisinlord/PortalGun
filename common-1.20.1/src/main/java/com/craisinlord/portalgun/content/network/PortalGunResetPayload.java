package com.craisinlord.portalgun.content.network;

import com.craisinlord.portalgun.PortalGun;
import io.netty.buffer.ByteBuf;
import com.craisinlord.anthology.network.ByteBufCodecs;
import com.craisinlord.anthology.network.StreamCodec;
import com.craisinlord.anthology.network.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PortalGunResetPayload(int action) implements CustomPacketPayload {
    public static final Type<PortalGunResetPayload> TYPE = new Type<>(new ResourceLocation(PortalGun.MODID, "portal_gun_reset"));
    public static final StreamCodec<ByteBuf, PortalGunResetPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, PortalGunResetPayload::action, PortalGunResetPayload::new
    );

    @Override
    public Type<PortalGunResetPayload> type() {
        return TYPE;
    }
}
