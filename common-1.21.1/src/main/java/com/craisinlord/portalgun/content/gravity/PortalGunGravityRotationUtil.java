package com.craisinlord.portalgun.content.gravity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public final class PortalGunGravityRotationUtil {
    private PortalGunGravityRotationUtil() { }
    public static Vec3 vecPlayerToWorld(Vec3 vector, PortalGunGravityDirection direction) { return vector; }
    public static Vec3 vecPlayerToWorld(double x, double y, double z, PortalGunGravityDirection direction) { return new Vec3(x, y, z); }
    public static Vec3 vecWorldToPlayer(Vec3 vector, PortalGunGravityDirection direction) { return vector; }
    public static Vec3 vecWorldToPlayer(double x, double y, double z, PortalGunGravityDirection direction) { return new Vec3(x, y, z); }
    public static Vec3 getEyeOffset(Entity entity, double eyeHeight) { return new Vec3(0, eyeHeight, 0); }
}
