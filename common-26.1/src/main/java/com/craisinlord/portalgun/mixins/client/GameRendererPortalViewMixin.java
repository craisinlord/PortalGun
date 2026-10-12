package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalViewRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererPortalViewMixin {
    @Unique
    private final Matrix4f portalgun$levelProjection = new Matrix4f();

    @ModifyArg(method = "renderLevel", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ProjectionMatrixBuffer;getBuffer(Lorg/joml/Matrix4f;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"))
    private Matrix4f portalgun$captureLevelProjection(Matrix4f projection) {
        this.portalgun$levelProjection.set(projection);
        return projection;
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/renderer/state/level/CameraRenderState;Lorg/joml/Matrix4fc;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;ZLnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;)V",
            shift = At.Shift.AFTER))
    private void portalgun$renderPortalViews(DeltaTracker deltaTracker, CallbackInfo ci) {
        GameRenderer renderer = (GameRenderer) (Object) this;
        PortalGunPortalViewRenderer.render(renderer.getGameRenderState().levelRenderState.cameraRenderState,
                this.portalgun$levelProjection, deltaTracker);
    }
}
