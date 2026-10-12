package com.craisinlord.portalgun.mixins.client;

import com.mojang.blaze3d.textures.GpuSampler;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.ViewArea;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface LevelRendererPortalViewAreaAccessor {
    @Accessor("viewArea")
    ViewArea portalgun$getViewArea();

    @Accessor("skyRenderer")
    SkyRenderer portalgun$getSkyRenderer();

    @Accessor("chunkLayerSampler")
    GpuSampler portalgun$getChunkLayerSampler();

    @Accessor("ticks")
    int portalgun$getTicks();

    @Accessor("lastCameraSectionX")
    int portalgun$getLastCameraSectionX();

    @Accessor("lastCameraSectionY")
    int portalgun$getLastCameraSectionY();

    @Accessor("lastCameraSectionZ")
    int portalgun$getLastCameraSectionZ();
}
