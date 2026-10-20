package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.PortalGunZoomClientState;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class PortalGunZoomFovMixin {
    @ModifyReturnValue(method = {"calculateFov", "calculateHudFov"}, at = @At("RETURN"))
    private float portalgun$applyPortalGunZoom(float fov, float partialTick) {
        return (float) PortalGunZoomClientState.applyFov(fov, partialTick, Minecraft.getInstance().options);
    }
}
