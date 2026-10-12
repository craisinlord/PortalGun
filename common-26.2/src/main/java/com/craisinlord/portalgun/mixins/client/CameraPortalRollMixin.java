package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.PortalGunRollClientState;
import net.minecraft.client.Camera;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraPortalRollMixin {
    @Shadow
    @Final
    private Quaternionf rotation;

    @Inject(method = "alignWithEntity", at = @At("TAIL"))
    private void portalgun$applyPortalRoll(float partialTick, CallbackInfo ci) {
        float roll = PortalGunRollClientState.getRollDegrees(partialTick);
        if (roll != 0.0F) {
            this.rotation.premul(new Quaternionf().fromAxisAngleDeg(0.0F, 0.0F, -1.0F, roll));
        }
    }
}
