package com.craisinlord.portalgun.neoforge;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = PortalGun.MODID)
public final class PortalGunNeoForgeGameplayEvents {
    private PortalGunNeoForgeGameplayEvents() { }

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
}
