package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.PortalGunClientTransit;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerPortalTransitMixin {
    @Unique
    private AABB portalgun$portalTransitStartBox;
    @Unique
    private Vec3 portalgun$portalTransitStartVelocity;

    @Inject(method = "move", at = @At("HEAD"))
    private void portalgun$capturePortalTransitStart(MoverType type, Vec3 movement, CallbackInfo ci) {
        this.portalgun$portalTransitStartBox = ((LocalPlayer) (Object) this).getBoundingBox();
        this.portalgun$portalTransitStartVelocity = ((LocalPlayer) (Object) this).getDeltaMovement();
    }

    @Inject(method = "move", at = @At("RETURN"))
    private void portalgun$applyPortalTransit(MoverType type, Vec3 movement, CallbackInfo ci) {
        AABB startBox = this.portalgun$portalTransitStartBox;
        Vec3 startVelocity = this.portalgun$portalTransitStartVelocity;
        this.portalgun$portalTransitStartBox = null;
        this.portalgun$portalTransitStartVelocity = null;
        if (startBox != null && startVelocity != null && type == MoverType.SELF) {
            PortalGunClientTransit.afterLocalMove((LocalPlayer) (Object) this, startBox, startVelocity);
        }
    }
}
