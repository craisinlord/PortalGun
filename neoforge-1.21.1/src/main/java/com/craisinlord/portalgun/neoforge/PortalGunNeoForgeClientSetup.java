package com.craisinlord.portalgun.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import com.craisinlord.portalgun.content.client.renderer.PortalGunPortalRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunProjectileRenderer;
import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.client.PortalGunAnthologyClientHooks;
import com.craisinlord.portalgun.content.client.PortalGunCrosshairRenderer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import com.craisinlord.portalgun.content.client.PortalGunIndicatorClientState;
import com.craisinlord.portalgun.content.network.PortalGunMoonAimPayload;
import com.craisinlord.portalgun.content.portalgun.PortalGunMoonAimSync;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

@EventBusSubscriber(modid = "portal_gun", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class PortalGunNeoForgeClientSetup {
    public static final KeyMapping ZOOM = new KeyMapping("key.portal_gun.zoom", KeyConflictContext.IN_GAME, InputConstants.Type.MOUSE, InputConstants.MOUSE_BUTTON_MIDDLE, "key.categories.portal_gun");
    public static final KeyMapping RESET = new KeyMapping("key.portal_gun.reset", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_R, "key.categories.portal_gun");
    private PortalGunNeoForgeClientSetup() { }
    @SubscribeEvent public static void registerKeyBindings(RegisterKeyMappingsEvent event) { event.register(ZOOM); event.register(RESET); }
    @SubscribeEvent public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PortalGunNeoForgeContent.PORTAL_ENTITY.get(), PortalGunPortalRenderer::new);
        event.registerEntityRenderer(PortalGunNeoForgeContent.PROJECTILE_ENTITY.get(), PortalGunProjectileRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        PortalGunAnthologyClientHooks.register();
        PortalGunIndicatorClientState.setRequestSender(PacketDistributor::sendToServer);
        PortalGunMoonAimSync.setClientReporter(() -> PacketDistributor.sendToServer(new PortalGunMoonAimPayload(false, false)));
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, "portal_gun_crosshair"),
                (graphics, partialTick) -> PortalGunCrosshairRenderer.render(graphics));
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        registerGunExtension(event, PortalGunNeoForgeContent.PORTAL_GUN.get());
        registerGunExtension(event, PortalGunNeoForgeContent.PORTAL_GUN_ATLAS.get());
        registerGunExtension(event, PortalGunNeoForgeContent.PORTAL_GUN_P_BODY.get());
        registerGunExtension(event, PortalGunNeoForgeContent.PORTAL_GUN_CRAISIN.get());
        registerGunExtension(event, PortalGunNeoForgeContent.PORTAL_GUN_LAYNCE.get());
    }

    private static void registerGunExtension(RegisterClientExtensionsEvent event, Item item) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return GeoRenderProvider.of(item).getGeoItemRenderer();
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        }, item);
    }
}
