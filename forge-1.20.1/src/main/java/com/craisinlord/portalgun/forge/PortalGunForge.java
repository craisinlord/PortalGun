package com.craisinlord.portalgun.forge;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.PortalGunAnthologyHooks;
import com.craisinlord.portalgun.content.network.PortalGunNetwork;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PortalGun.MODID)
public final class PortalGunForge {
    public PortalGunForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        PortalGunAnthologyHooks.register();
        PortalGunNetwork.register();
        PortalGunForgeContent.register(modBus);
        modBus.addListener(PortalGunForge::creativeTab);
    }

    private static void creativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.accept(PortalGunForgeContent.PORTAL_GUN);
            event.accept(PortalGunForgeContent.PORTAL_GUN_ATLAS);
            event.accept(PortalGunForgeContent.PORTAL_GUN_P_BODY);
            event.accept(PortalGunForgeContent.PORTAL_GUN_CRAISIN);
            event.accept(PortalGunForgeContent.PORTAL_GUN_LAYNCE);
        }
    }
}
