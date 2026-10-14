package com.craisinlord.portalgun.fabric.client;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.registry.PortalGunContent;
import java.util.Collection;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class SeparateLargeItemModels {
    private static Map<Item, ResourceLocation> guiModels;

    private SeparateLargeItemModels() {
    }

    public static ResourceLocation modelFor(ItemStack stack) {
        return models().get(stack.getItem());
    }

    public static Collection<ResourceLocation> all() {
        return models().values();
    }

    private static Map<Item, ResourceLocation> models() {
        if (guiModels == null) {
            guiModels = Map.ofEntries(
                    entry(PortalGunContent.PORTAL_GUN, "portal_gun_gui"),
                    entry(PortalGunContent.PORTAL_GUN_ATLAS, "portal_gun_atlas_gui"),
                    entry(PortalGunContent.PORTAL_GUN_P_BODY, "portal_gun_p_body_gui"),
                    entry(PortalGunContent.PORTAL_GUN_CRAISIN, "portal_gun_craisin_gui"),
                    entry(PortalGunContent.PORTAL_GUN_LAYNCE, "portal_gun_laynce_gui"));
        }
        return guiModels;
    }

    private static Map.Entry<Item, ResourceLocation> entry(Item item, String modelPath) {
        return Map.entry(item, new ResourceLocation(PortalGun.MODID, "item/" + modelPath));
    }
}
