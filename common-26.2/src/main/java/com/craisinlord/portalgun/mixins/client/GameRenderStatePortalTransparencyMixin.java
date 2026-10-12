package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalSceneRenderer;
import net.minecraft.client.renderer.state.GameRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderState.class)
public abstract class GameRenderStatePortalTransparencyMixin {
    @Inject(method = "useShaderTransparency", at = @At("HEAD"), cancellable = true)
    private void portalgun$disableShaderTransparencyInPortalScenes(CallbackInfoReturnable<Boolean> cir) {
        if (PortalGunPortalSceneRenderer.isRenderingScene()) {
            cir.setReturnValue(false);
        }
    }
}
