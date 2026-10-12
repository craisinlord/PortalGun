package com.craisinlord.portalgun.mixins;

import com.craisinlord.portalgun.content.portalgun.PortalGunCollisionHelper;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = 1100)
public abstract class PortalGunEntityCollisionMixin {
    @Unique
    private AABB portalgun$portalGunStartBox;
    @Unique
    private AABB portalgun$portalGunMoveStartBox;
    @Unique
    private Vec3 portalgun$portalGunMoveStartVelocity;

    @Inject(method = "move", at = @At("HEAD"))
    private void portalgun$capturePortalGunMovementStart(MoverType type, Vec3 movement, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!PortalGunPortalRegistry.hasAny(entity.level())) {
            this.portalgun$portalGunMoveStartBox = null;
            this.portalgun$portalGunMoveStartVelocity = null;
            return;
        }
        this.portalgun$portalGunMoveStartBox = entity.getBoundingBox();
        this.portalgun$portalGunMoveStartVelocity = entity.getDeltaMovement();
    }

    @Inject(method = "move", at = @At("RETURN"))
    private void portalgun$teleportMobOnPortalCrossing(MoverType type, Vec3 movement, CallbackInfo ci) {
        if (this.portalgun$portalGunMoveStartBox == null) {
            return;
        }
        AABB startBox = this.portalgun$portalGunMoveStartBox;
        Vec3 startVelocity = this.portalgun$portalGunMoveStartVelocity;
        this.portalgun$portalGunMoveStartBox = null;
        this.portalgun$portalGunMoveStartVelocity = null;
        if (startBox != null && startVelocity != null) {
            PortalGunPortalEntity.teleportEntityAfterMovement((Entity) (Object) this, startBox, startVelocity);
        }
    }

    @Inject(method = "collide", at = @At("HEAD"))
    private void portalgun$capturePortalGunStartBox(Vec3 movement, CallbackInfoReturnable<Vec3> cir) {
        Entity entity = (Entity) (Object) this;
        this.portalgun$portalGunStartBox = PortalGunPortalRegistry.hasAny(entity.level()) ? entity.getBoundingBox() : null;
    }

    @Inject(method = "collide", at = @At("RETURN"), cancellable = true)
    private void portalgun$restorePortalCrossingMotion(Vec3 movement, CallbackInfoReturnable<Vec3> cir) {
        if (this.portalgun$portalGunStartBox == null) {
            return;
        }
        Entity entity = (Entity) (Object) this;
        Vec3 collided = cir.getReturnValue();
        Vec3 adjusted = PortalGunCollisionHelper.resolveCollision(entity, this.portalgun$portalGunStartBox, movement, collided);
        if (adjusted != collided) {
            cir.setReturnValue(adjusted);
        }
    }

}
