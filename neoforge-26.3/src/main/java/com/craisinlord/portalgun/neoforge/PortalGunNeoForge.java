package com.craisinlord.portalgun.neoforge;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.PortalGunAnthologyHooks;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(PortalGun.MODID)
public final class PortalGunNeoForge {
    public PortalGunNeoForge(IEventBus modBus) {
        PortalGunAnthologyHooks.register();
        PortalGunNeoForgeContent.register(modBus);
        modBus.addListener(PortalGunNetworking::register);
        modBus.addListener(PortalGunNeoForge::creativeTab);
    }

    private static void creativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(PortalGunNeoForgeContent.PORTAL_GUN);
            event.accept(PortalGunNeoForgeContent.PORTAL_GUN_ATLAS);
            event.accept(PortalGunNeoForgeContent.PORTAL_GUN_P_BODY);
            event.accept(PortalGunNeoForgeContent.PORTAL_GUN_CRAISIN);
            event.accept(PortalGunNeoForgeContent.PORTAL_GUN_LAYNCE);
        }
    }
}
