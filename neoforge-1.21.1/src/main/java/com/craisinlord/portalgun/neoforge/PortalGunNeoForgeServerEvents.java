package com.craisinlord.portalgun.neoforge;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.command.PortalGunCommand;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import com.craisinlord.portalgun.content.portalgun.PortalGunProjectileEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunResetManager;
import com.craisinlord.portalgun.content.portalgun.PortalGunSavedData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@EventBusSubscriber(modid = PortalGun.MODID)
public final class PortalGunNeoForgeServerEvents {
    private PortalGunNeoForgeServerEvents() { }
    @SubscribeEvent public static void onServerStopping(ServerStoppingEvent event) {
        PortalGunProjectileEntity.releaseAllProjectileTickets();
        PortalGunResetManager.clear(event.getServer());
        PortalGunMoonAimSync.clear();
    }
    @SubscribeEvent public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) PortalGunSavedData.clearMissingGuns(player);
    }
    @SubscribeEvent public static void onRegisterCommands(RegisterCommandsEvent event) {
        PortalGunCommand.register(event.getDispatcher());
    }
}
