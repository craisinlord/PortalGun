package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalViewRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererPortalLifecycleMixin {
    @Inject(method = "resetLevelRenderData", at = @At("HEAD"))
    private void portalgun$clearPortalViewsOnLevelChange(CallbackInfo ci) {
        PortalGunPortalViewRenderer.releaseTargets();
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void portalgun$clearPortalViewsOnClose(CallbackInfo ci) {
        PortalGunPortalViewRenderer.releaseTargets();
        PortalGunPortalViewRenderer.close();
    }

    @Inject(method = "invalidateCompiledGeometry", at = @At("TAIL"))
    private void portalgun$clearPortalViewsOnRendererRebuild(CallbackInfo ci) {
        PortalGunPortalViewRenderer.releaseTargets();
    }
}
