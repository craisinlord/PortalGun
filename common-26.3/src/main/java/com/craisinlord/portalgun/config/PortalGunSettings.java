package com.craisinlord.portalgun.config;

public final class PortalGunSettings {
    private PortalGunSettings() { }
    public static boolean portalGunCanFireThroughGlass() { return false; }
    public static boolean portalGunCanFireThroughLiquid() { return false; }
    public static boolean portalGunCanPortalsResizeWhenCreated() { return true; }
    public static boolean portalGunCanPortalProjectilesChunkload() { return true; }
    public static boolean portalGunSeeThroughPortals() { return true; }
    public static boolean portalGunFancyPortals() { return true; }
    public static int portalGunMaxShootDistance() { return 10000; }
    public static double portalGunMinFloorExitSpeed() { return 0.6D; }
    public static int portalGunMaxLiveViews() { return 8; }
    public static double portalGunMaxExitSpeed() { return 0.0D; }
    public static boolean portalGunMoonPullsBlocks() { return true; }
    public static int portalGunMoonPortalSeconds() { return 10; }
    public static int portalGunMoonBlockCap() { return 500; }
}
