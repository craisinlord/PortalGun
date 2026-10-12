package com.craisinlord.portalgun.mixins.client;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.renderer.SectionOcclusionGraph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SectionOcclusionGraph.class)
public interface SectionOcclusionGraphPortalAccessor {
    @Accessor("emptySections")
    LongOpenHashSet portalgun$getEmptySections();

    @Accessor("loadedChunks")
    LongOpenHashSet portalgun$getLoadedChunks();
}
