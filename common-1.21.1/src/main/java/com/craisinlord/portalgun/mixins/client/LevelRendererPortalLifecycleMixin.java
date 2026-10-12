package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalViewRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalStencilTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererPortalLifecycleMixin {
    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void portalgun$preparePortalStencil(CallbackInfo ci) {
        if (PortalGunPortalViewRenderer.isEnabled()) {
            PortalStencilTarget.prepareMainTarget(Minecraft.getInstance().getMainRenderTarget());
        }
    }

    @Inject(method = "setLevel", at = @At("HEAD"))
    private void portalgun$clearPortalViewsOnLevelChange(ClientLevel level, CallbackInfo ci) {
        PortalGunPortalViewRenderer.releaseTargets();
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void portalgun$clearPortalViewsOnClose(CallbackInfo ci) {
        PortalGunPortalViewRenderer.releaseTargets();
        PortalStencilTarget.clear();
    }

    @Inject(method = "allChanged", at = @At("TAIL"))
    private void portalgun$clearPortalViewsOnRendererRebuild(CallbackInfo ci) {
        PortalGunPortalViewRenderer.releaseTargets();
    }
}
