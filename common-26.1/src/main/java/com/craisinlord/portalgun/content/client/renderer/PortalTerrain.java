package com.craisinlord.portalgun.content.client.renderer;

import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4fc;

public interface PortalTerrain {
    static boolean canView(Minecraft minecraft, Vec3 exitCenter) {
        return !SodiumCompat.isLoaded() && PortalGunPortalSectionViews.canView(minecraft, exitCenter);
    }

    static PortalTerrain prepare(Minecraft minecraft, java.util.UUID exitId, Vec3 exitCenter, Vec3 exitNormal, Frustum frustum, Vec3 cameraPosition) {
        return VanillaPortalTerrain.prepare(minecraft, exitId, exitCenter, exitNormal, frustum, cameraPosition);
    }

    int sectionCount();

    void begin(Minecraft minecraft, Matrix4fc viewMatrix);

    void end(Minecraft minecraft);

    void drawGroup(Minecraft minecraft, ChunkSectionLayerGroup group);

    void forEachBlockEntity(Minecraft minecraft, Frustum frustum, Consumer<BlockEntity> consumer);
}
