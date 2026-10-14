package com.craisinlord.portalgun.neoforge;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.client.PortalGunResetClientState;
import com.craisinlord.portalgun.content.client.PortalGunZoomClientState;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.network.PortalGunMoonAimPayload;
import com.craisinlord.portalgun.content.network.PortalGunPrimaryPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@EventBusSubscriber(modid = PortalGun.MODID, value = Dist.CLIENT)
public final class PortalGunNeoForgeClientEvents {
    private static boolean lastAttackDown;
    private PortalGunNeoForgeClientEvents() { }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) PortalGunResetClientState.clear();
        boolean hasGun = mc.player != null && (mc.player.getMainHandItem().getItem() instanceof PortalGunItem || mc.player.getOffhandItem().getItem() instanceof PortalGunItem);
        boolean input = mc.screen == null;
        PortalGunZoomClientState.tick(mc, hasGun, PortalGunNeoForgeClientSetup.ZOOM.consumeClick() && input);
        boolean resetting = PortalGunResetClientState.tick(mc, hasGun && input,
                PortalGunNeoForgeClientSetup.RESET.isDown() || PortalGunNeoForgeClientSetup.RESET.consumeClick(), payload -> ClientPacketDistributor.sendToServer(payload));
        if (mc.player == null || mc.level == null || !input) { lastAttackDown = false; return; }
        boolean offhand = !(mc.player.getMainHandItem().getItem() instanceof PortalGunItem) && mc.player.getOffhandItem().getItem() instanceof PortalGunItem;
        if (!offhand && !(mc.player.getMainHandItem().getItem() instanceof PortalGunItem)) { lastAttackDown = false; return; }
        boolean attack = mc.options.keyAttack.isDown();
        if (attack && !lastAttackDown && !resetting) {
            ClientPacketDistributor.sendToServer(new PortalGunMoonAimPayload(false, false));
            ClientPacketDistributor.sendToServer(new PortalGunPrimaryPayload(offhand));
        }
        lastAttackDown = attack;
    }

}
