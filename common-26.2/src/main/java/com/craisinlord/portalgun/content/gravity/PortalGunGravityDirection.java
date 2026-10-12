package com.craisinlord.portalgun.content.gravity;

public enum PortalGunGravityDirection {
    DOWN(0, false),
    UP(1, true);

    private final int id;
    private final boolean inverted;

    PortalGunGravityDirection(int id, boolean inverted) {
        this.id = id;
        this.inverted = inverted;
    }

    public int getId() { return id; }
    public boolean isInverted() { return inverted; }
    public static PortalGunGravityDirection byId(int id) { return id == UP.id ? UP : DOWN; }
}
