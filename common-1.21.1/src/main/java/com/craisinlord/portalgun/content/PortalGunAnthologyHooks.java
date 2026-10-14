package com.craisinlord.portalgun.content;

import com.craisinlord.anthology.portal.PortalHooks;
import com.craisinlord.anthology.portal.PortalSettings;
import com.craisinlord.portalgun.config.PortalGunSettings;
import com.craisinlord.portalgun.content.portalgun.PortalGunProjectileEntity;

public final class PortalGunAnthologyHooks {
    private PortalGunAnthologyHooks() {
    }

    public static void register() {
        PortalHooks.setSettings(new PortalGunPortalSettings());
        PortalHooks.addTransitExclusion(entity -> entity instanceof PortalGunProjectileEntity);
    }

    private static final class PortalGunPortalSettings implements PortalSettings {
        @Override
        public boolean seeThroughEnabled() {
            return PortalGunSettings.portalGunSeeThroughPortals();
        }

        @Override
        public int maxLiveViews() {
            return PortalGunSettings.portalGunMaxLiveViews();
        }

        @Override
        public double minFloorExitSpeed() {
            return PortalGunSettings.portalGunMinFloorExitSpeed();
        }

        @Override
        public double maxExitSpeed() {
            return PortalGunSettings.portalGunMaxExitSpeed();
        }

        @Override
        public boolean fancySurfaces() {
            return PortalGunSettings.portalGunFancyPortals();
        }
    }
}
