package com.craisinlord.portalgun.mixins.client;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SectionOcclusionGraph;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import java.util.Set;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface LevelRendererPortalViewAreaAccessor {
    @Accessor("sectionRenderDispatcher")
    SectionRenderDispatcher portalgun$getSectionRenderDispatcher();

    @Accessor("viewArea")
    ViewArea portalgun$getViewArea();

    @Accessor("viewArea")
    void portalgun$setViewArea(ViewArea viewArea);

    @Accessor("sectionOcclusionGraph")
    SectionOcclusionGraph portalgun$getSectionOcclusionGraph();

    @Mutable
    @Accessor("sectionOcclusionGraph")
    void portalgun$setSectionOcclusionGraph(SectionOcclusionGraph graph);

    @Accessor("visibleSections")
    ObjectArrayList<SectionRenderDispatcher.RenderSection> portalgun$getVisibleSections();

    @Mutable
    @Accessor("visibleSections")
    void portalgun$setVisibleSections(ObjectArrayList<SectionRenderDispatcher.RenderSection> sections);

    @Accessor("globalBlockEntities")
    Set<BlockEntity> portalgun$getGlobalBlockEntities();

    @Mutable
    @Accessor("globalBlockEntities")
    void portalgun$setGlobalBlockEntities(Set<BlockEntity> blockEntities);

    @Accessor("lastCameraSectionX")
    int portalgun$getLastCameraSectionX();

    @Accessor("lastCameraSectionX")
    void portalgun$setLastCameraSectionX(int value);

    @Accessor("lastCameraSectionY")
    int portalgun$getLastCameraSectionY();

    @Accessor("lastCameraSectionY")
    void portalgun$setLastCameraSectionY(int value);

    @Accessor("lastCameraSectionZ")
    int portalgun$getLastCameraSectionZ();

    @Accessor("lastCameraSectionZ")
    void portalgun$setLastCameraSectionZ(int value);

    @Accessor("prevCamX")
    double portalgun$getPrevCamX();

    @Accessor("prevCamX")
    void portalgun$setPrevCamX(double value);

    @Accessor("prevCamY")
    double portalgun$getPrevCamY();

    @Accessor("prevCamY")
    void portalgun$setPrevCamY(double value);

    @Accessor("prevCamZ")
    double portalgun$getPrevCamZ();

    @Accessor("prevCamZ")
    void portalgun$setPrevCamZ(double value);

    @Accessor("prevCamRotX")
    double portalgun$getPrevCamRotX();

    @Accessor("prevCamRotX")
    void portalgun$setPrevCamRotX(double value);

    @Accessor("prevCamRotY")
    double portalgun$getPrevCamRotY();

    @Accessor("prevCamRotY")
    void portalgun$setPrevCamRotY(double value);

    @Accessor("xTransparentOld")
    double portalgun$getTransparentSortX();

    @Accessor("xTransparentOld")
    void portalgun$setTransparentSortX(double value);

    @Accessor("yTransparentOld")
    double portalgun$getTransparentSortY();

    @Accessor("yTransparentOld")
    void portalgun$setTransparentSortY(double value);

    @Accessor("zTransparentOld")
    double portalgun$getTransparentSortZ();

    @Accessor("zTransparentOld")
    void portalgun$setTransparentSortZ(double value);

    @Accessor("cloudBuffer")
    VertexBuffer portalgun$getCloudBuffer();

    @Accessor("cloudBuffer")
    void portalgun$setCloudBuffer(VertexBuffer cloudBuffer);

    @Accessor("generateClouds")
    boolean portalgun$getGenerateClouds();

    @Accessor("generateClouds")
    void portalgun$setGenerateClouds(boolean generateClouds);

    @Accessor("prevCloudX")
    int portalgun$getPrevCloudX();

    @Accessor("prevCloudX")
    void portalgun$setPrevCloudX(int value);

    @Accessor("prevCloudY")
    int portalgun$getPrevCloudY();

    @Accessor("prevCloudY")
    void portalgun$setPrevCloudY(int value);

    @Accessor("prevCloudZ")
    int portalgun$getPrevCloudZ();

    @Accessor("prevCloudZ")
    void portalgun$setPrevCloudZ(int value);

    @Accessor("prevCloudColor")
    Vec3 portalgun$getPrevCloudColor();

    @Accessor("prevCloudColor")
    void portalgun$setPrevCloudColor(Vec3 value);

    @Accessor("prevCloudsType")
    CloudStatus portalgun$getPrevCloudsType();

    @Accessor("prevCloudsType")
    void portalgun$setPrevCloudsType(CloudStatus value);

    @Accessor("rainSoundTime")
    int portalgun$getRainSoundTime();

    @Accessor("rainSoundTime")
    void portalgun$setRainSoundTime(int value);

}
