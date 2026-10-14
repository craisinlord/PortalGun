package com.craisinlord.portalgun.content.client.renderer;

import com.craisinlord.portalgun.content.client.model.ResourceBackedGeoItemModel;
import com.craisinlord.portalgun.content.item.PortalGunItem;

public final class PortalGunItemRenderers {
    private PortalGunItemRenderers() {
    }

    public static AnimatedHeldItemRenderer<PortalGunItem> create(PortalGunItem item) {
        return new AnimatedHeldItemRenderer<>(new ResourceBackedGeoItemModel<>(PortalGunItem.MODEL_LOCATION,
                item.getVariant().modelTexture(), PortalGunItem.ANIMATION_LOCATION));
    }
}
