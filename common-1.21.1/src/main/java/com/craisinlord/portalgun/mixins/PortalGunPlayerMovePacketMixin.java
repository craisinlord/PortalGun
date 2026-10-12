package com.craisinlord.portalgun.mixins;

import com.craisinlord.portalgun.content.portalgun.PortalGunPortalEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalRegistry;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class PortalGunPlayerMovePacketMixin {
    @Unique
    private static final double MAX_CLAIMED_MOVEMENT_SQR = 100.0D;
    @Unique
    private AABB portalgun$portalGunMoveStartBox;
    @Unique
    private Vec3 portalgun$portalGunMoveStartPosition;

    @Inject(method = "handleMovePlayer", at = @At("HEAD"))
    private void portalgun$capturePortalGunMoveStart(ServerboundMovePlayerPacket packet, CallbackInfo ci) {
        ServerPlayer player = ((ServerGamePacketListenerImpl) (Object) this).player;
        if (player.getServer() == null || !player.getServer().isSameThread()) {
            return;
        }
        this.portalgun$portalGunMoveStartBox = null;
        this.portalgun$portalGunMoveStartPosition = null;
        if (!packet.hasPosition() || !PortalGunPortalRegistry.hasAny(player.level()) || ((ServerGamePacketListenerTeleportAccessor) this).portalgun$getAwaitingPositionFromClient() != null) {
            return;
        }
        this.portalgun$portalGunMoveStartBox = player.getBoundingBox();
        this.portalgun$portalGunMoveStartPosition = player.position();
    }

    @Inject(method = "handleMovePlayer", at = @At("RETURN"))
    private void portalgun$teleportPlayerOnPortalCrossing(ServerboundMovePlayerPacket packet, CallbackInfo ci) {
        AABB startBox = this.portalgun$portalGunMoveStartBox;
        Vec3 startPosition = this.portalgun$portalGunMoveStartPosition;
        this.portalgun$portalGunMoveStartBox = null;
        this.portalgun$portalGunMoveStartPosition = null;
        if (startBox == null || startPosition == null) {
            return;
        }
        ServerPlayer player = ((ServerGamePacketListenerImpl) (Object) this).player;
        Vec3 claimedMovement = new Vec3(packet.getX(startPosition.x), packet.getY(startPosition.y), packet.getZ(startPosition.z))
                .subtract(startPosition);
        double lengthSqr = claimedMovement.lengthSqr();
        if (lengthSqr > 1.0E-10D && lengthSqr < MAX_CLAIMED_MOVEMENT_SQR) {
            PortalGunPortalEntity.teleportPlayerAfterMovementPacket(player, startBox, claimedMovement);
        }
    }
}
