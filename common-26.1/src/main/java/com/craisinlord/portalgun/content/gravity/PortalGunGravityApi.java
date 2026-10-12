package com.craisinlord.portalgun.content.gravity;

import net.minecraft.world.entity.Entity;

public final class PortalGunGravityApi {
    private PortalGunGravityApi() { }
    public static PortalGunGravityDirection getGravityDirection(Entity entity) { return PortalGunGravityDirection.DOWN; }
    public static boolean isGravityInverted(Entity entity) { return false; }
    public static float getGravityFlipProgress(Entity entity, float partialTick) { return 0.0F; }
    public static void setAirborneGravityDirection(Entity entity, PortalGunGravityDirection direction, boolean forced, PortalGunGravityTransition transition) { }
}
