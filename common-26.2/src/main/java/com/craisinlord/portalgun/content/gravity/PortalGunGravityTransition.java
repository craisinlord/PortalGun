package com.craisinlord.portalgun.content.gravity;

public record PortalGunGravityTransition(int durationTicks) {
    public static final PortalGunGravityTransition INSTANT = new PortalGunGravityTransition(0);
    public PortalGunGravityTransition { durationTicks = Math.max(0, durationTicks); }
}
