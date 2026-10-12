package com.craisinlord.portalgun.content.client.model;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PortalGunPortalModel extends GeoModel<PortalGunPortalEntity> {
    private static final ResourceLocation BLUE_TEXTURE = ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, "textures/vfx/portal_gun_portal_blue.png");
    private static final ResourceLocation ORANGE_TEXTURE = ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, "textures/vfx/portal_gun_portal_orange.png");

    @Override
    public ResourceLocation getModelResource(PortalGunPortalEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, "geo/portal_gun_portal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PortalGunPortalEntity animatable) {
        return animatable.getPortalSide() == PortalGunPortalEntity.PortalSide.BLUE ? BLUE_TEXTURE : ORANGE_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(PortalGunPortalEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, "animations/portal_gun_portal.animation.json");
    }
}
