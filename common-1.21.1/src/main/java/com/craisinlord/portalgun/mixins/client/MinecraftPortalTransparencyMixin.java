package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalSceneRenderer;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftPortalTransparencyMixin {
    @Inject(method = "useShaderTransparency", at = @At("HEAD"), cancellable = true)
    private static void portalgun$disableShaderTransparencyInPortalScenes(CallbackInfoReturnable<Boolean> cir) {
        if (PortalGunPortalSceneRenderer.isRenderingScene()) {
            cir.setReturnValue(false);
        }
    }
}
