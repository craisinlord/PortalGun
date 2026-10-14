package com.craisinlord.portalgun.fabric;

import com.craisinlord.portalgun.content.PortalGunAnthologyHooks;
import com.craisinlord.portalgun.content.command.PortalGunCommand;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.network.PortalGunNetwork;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import com.craisinlord.portalgun.content.portalgun.PortalGunProjectileEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunResetManager;
import com.craisinlord.portalgun.content.portalgun.PortalGunSavedData;
import com.craisinlord.portalgun.registry.PortalGunContent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTabs;

public final class PortalGunFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PortalGunContent.register();
        PortalGunAnthologyHooks.register();
        PortalGunNetwork.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> PortalGunCommand.register(dispatcher));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> PortalGunProjectileEntity.releaseAllProjectileTickets());
        ServerLifecycleEvents.SERVER_STOPPING.register(PortalGunResetManager::clear);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> PortalGunMoonAimSync.clear());
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> PortalGunSavedData.clearMissingGuns(handler.player));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(PortalGunContent.PORTAL_GUN);
            entries.accept(PortalGunContent.PORTAL_GUN_ATLAS);
            entries.accept(PortalGunContent.PORTAL_GUN_P_BODY);
            entries.accept(PortalGunContent.PORTAL_GUN_CRAISIN);
            entries.accept(PortalGunContent.PORTAL_GUN_LAYNCE);
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
                player.getItemInHand(hand).getItem() instanceof PortalGunItem ? InteractionResult.FAIL : InteractionResult.PASS);
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                player.getItemInHand(hand).getItem() instanceof PortalGunItem ? InteractionResult.FAIL : InteractionResult.PASS);
    }
}
