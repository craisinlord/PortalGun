package com.craisinlord.portalgun.fabric;

import com.craisinlord.portalgun.content.PortalGunAnthologyHooks;
import com.craisinlord.portalgun.registry.PortalGunContent;
import com.craisinlord.portalgun.content.command.PortalGunCommand;
import com.craisinlord.portalgun.content.portalgun.PortalGunProjectileEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunResetManager;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import com.craisinlord.portalgun.fabric.network.PortalGunFabricNetworking;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.InteractionResult;

public final class PortalGunFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PortalGunContent.register(new PortalGunContent.BlockEntityTypeFactory() {
            @Override
            public <T extends BlockEntity> BlockEntityType<T> create(BiFunction<BlockPos, BlockState, T> factory, Block block) {
                return FabricBlockEntityTypeBuilder.<T>create(factory::apply, block).build();
            }
        });
        PortalGunAnthologyHooks.register();
        PortalGunFabricNetworking.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> PortalGunCommand.register(dispatcher));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> PortalGunProjectileEntity.releaseAllProjectileTickets());
        ServerLifecycleEvents.SERVER_STOPPING.register(PortalGunResetManager::clear);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> PortalGunMoonAimSync.clear());
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> com.craisinlord.portalgun.content.portalgun.PortalGunSavedData.clearMissingGuns(handler.player));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(PortalGunContent.PORTAL_GUN);
            entries.accept(PortalGunContent.PORTAL_GUN_ATLAS);
            entries.accept(PortalGunContent.PORTAL_GUN_P_BODY);
            entries.accept(PortalGunContent.PORTAL_GUN_CRAISIN);
            entries.accept(PortalGunContent.PORTAL_GUN_LAYNCE);
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
                player.getItemInHand(hand).getItem() instanceof com.craisinlord.portalgun.content.item.PortalGunItem
                        ? InteractionResult.FAIL : InteractionResult.PASS);
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                player.getItemInHand(hand).getItem() instanceof com.craisinlord.portalgun.content.item.PortalGunItem
                        ? InteractionResult.FAIL : InteractionResult.PASS);
    }
}
