package com.craisinlord.portalgun.mixins.client;

import com.mojang.blaze3d.textures.GpuSampler;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface LevelRendererPortalViewAreaAccessor {
    @Accessor("chunkLayerSampler")
    GpuSampler portalgun$getChunkLayerSampler();
}
