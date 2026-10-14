package com.craisinlord.portalgun.content.client;

import com.craisinlord.anthology.portal.PortalEntity;
import com.craisinlord.anthology.portal.client.PortalClientHooks;
import com.craisinlord.portalgun.content.client.renderer.PortalGunMoonViewRenderer;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalEntity;
import java.util.UUID;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

public final class PortalGunAnthologyClientHooks {
    private PortalGunAnthologyClientHooks() {
    }

    public static void register() {
        PortalClientHooks.setOutlinePolicy(PortalGunAnthologyClientHooks::outlinesPortal);
        PortalClientHooks.registerCustomView(portal -> portal instanceof PortalGunPortalEntity gunPortal && gunPortal.isMoonPortal(),
                PortalGunMoonViewRenderer::render);
    }

    private static boolean outlinesPortal(PortalEntity portal, LocalPlayer viewer) {
        if (!(portal instanceof PortalGunPortalEntity gunPortal) || gunPortal.getOwnerId() == null) {
            return false;
        }
        UUID owner = gunPortal.getOwnerId();
        return owner.equals(viewer.getUUID())
                || owner.equals(heldGunOwner(viewer.getMainHandItem(), viewer.getUUID()))
                || owner.equals(heldGunOwner(viewer.getOffhandItem(), viewer.getUUID()));
    }

    private static UUID heldGunOwner(ItemStack stack, UUID fallback) {
        return stack.getItem() instanceof PortalGunItem ? PortalGunItem.getPortalOwnerId(stack, fallback) : null;
    }
}
