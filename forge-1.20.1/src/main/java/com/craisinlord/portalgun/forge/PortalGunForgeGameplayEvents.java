package com.craisinlord.portalgun.forge;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.command.PortalGunCommand;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import com.craisinlord.portalgun.content.portalgun.PortalGunProjectileEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunResetManager;
import com.craisinlord.portalgun.content.portalgun.PortalGunSavedData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PortalGun.MODID)
public final class PortalGunForgeGameplayEvents {
    private PortalGunForgeGameplayEvents() {
    }

    @SubscribeEvent
    public static void onAttackBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getItemStack().getItem() instanceof PortalGunItem) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (event.getEntity().getMainHandItem().getItem() instanceof PortalGunItem
                || event.getEntity().getOffhandItem().getItem() instanceof PortalGunItem) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        PortalGunProjectileEntity.releaseAllProjectileTickets();
        PortalGunResetManager.clear(event.getServer());
        PortalGunMoonAimSync.clear();
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PortalGunSavedData.clearMissingGuns(player);
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        PortalGunCommand.register(event.getDispatcher());
    }
}
