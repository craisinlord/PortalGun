package com.craisinlord.portalgun.forge.client;

import com.craisinlord.anthology.network.AnthologyNetwork;
import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.client.PortalGunResetClientState;
import com.craisinlord.portalgun.content.client.PortalGunZoomClientState;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.network.PortalGunMoonAimPayload;
import com.craisinlord.portalgun.content.network.PortalGunPrimaryPayload;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PortalGun.MODID, value = Dist.CLIENT)
public final class PortalGunForgeClientEvents {
    private static boolean lastAttackDown;

    private PortalGunForgeClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            PortalGunResetClientState.clear();
        }
        boolean hasGun = mc.player != null && (mc.player.getMainHandItem().getItem() instanceof PortalGunItem || mc.player.getOffhandItem().getItem() instanceof PortalGunItem);
        boolean input = mc.screen == null;
        PortalGunZoomClientState.tick(mc, hasGun, PortalGunForgeClientSetup.ZOOM.consumeClick() && input);
        boolean resetting = PortalGunResetClientState.tick(mc, hasGun && input,
                PortalGunForgeClientSetup.RESET.isDown() || PortalGunForgeClientSetup.RESET.consumeClick(), AnthologyNetwork::sendToServer);
        if (mc.player == null || mc.level == null || !input) {
            lastAttackDown = false;
            return;
        }
        boolean offhand = !(mc.player.getMainHandItem().getItem() instanceof PortalGunItem) && mc.player.getOffhandItem().getItem() instanceof PortalGunItem;
        if (!offhand && !(mc.player.getMainHandItem().getItem() instanceof PortalGunItem)) {
            lastAttackDown = false;
            return;
        }
        boolean attack = mc.options.keyAttack.isDown();
        if (attack && !lastAttackDown && !resetting) {
            AnthologyNetwork.sendToServer(new PortalGunMoonAimPayload(false, false));
            AnthologyNetwork.sendToServer(new PortalGunPrimaryPayload(offhand));
        }
        lastAttackDown = attack;
    }
}
