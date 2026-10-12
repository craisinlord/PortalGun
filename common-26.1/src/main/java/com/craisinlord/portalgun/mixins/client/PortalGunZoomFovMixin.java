package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.PortalGunZoomClientState;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class PortalGunZoomFovMixin {
    @Inject(method = {"calculateFov", "calculateHudFov"}, at = @At("RETURN"), cancellable = true)
    private void portalgun$applyPortalGunZoom(float partialTick, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) PortalGunZoomClientState.applyFov(cir.getReturnValue(), partialTick, Minecraft.getInstance().options));
    }
}
