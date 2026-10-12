package com.craisinlord.portalgun.mixins.client;

import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalSectionViews;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererPortalViewAreaMixin {
    @Inject(method = "addRecentlyCompiledSection", at = @At("TAIL"))
    private void portalgun$notifyPortalGraphs(SectionRenderDispatcher.RenderSection section, CallbackInfo ci) {
        PortalGunPortalSectionViews.onSectionCompiled(section);
    }

    @Inject(method = "onChunkReadyToRender", at = @At("TAIL"))
    private void portalgun$updatePortalChunkGraph(ChunkPos pos, CallbackInfo ci) {
        PortalGunPortalSectionViews.onChunkLoaded(pos);
    }
}
