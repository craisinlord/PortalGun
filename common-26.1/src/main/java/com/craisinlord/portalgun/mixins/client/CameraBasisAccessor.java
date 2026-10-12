package com.craisinlord.portalgun.mixins.client;

import net.minecraft.client.Camera;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Camera.class)
public interface CameraBasisAccessor {
    @Accessor("forwards")
    Vector3f portalgun$getForwards();

    @Accessor("up")
    Vector3f portalgun$getUp();

    @Accessor("left")
    Vector3f portalgun$getLeft();
}
