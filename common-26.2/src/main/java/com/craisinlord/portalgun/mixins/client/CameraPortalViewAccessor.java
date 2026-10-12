package com.craisinlord.portalgun.mixins.client;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Camera.class)
public interface CameraPortalViewAccessor {
    @Accessor("initialized")
    void portalgun$setInitialized(boolean initialized);

    @Accessor("detached")
    void portalgun$setDetached(boolean detached);
}
