package com.craisinlord.portalgun.mixins.gravity;

import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerEntity.class)
public interface ServerEntityAccessor {
    @Accessor("positionCodec")
    VecDeltaCodec portalgun$getPositionCodec();

    @Accessor("lastSentMovement")
    void portalgun$setLastSentMovement(Vec3 lastSentMovement);

    @Accessor("updateInterval")
    void portalgun$setUpdateInterval(int updateInterval);
}
