package com.craisinlord.portalgun.content.client.model;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class PortalGunPortalModel extends GeoModel<PortalGunPortalEntity> {
    public static final DataTicket<PortalGunPortalEntity.PortalSide> PORTAL_SIDE = DataTicket.create("portal_gun_portal_side", PortalGunPortalEntity.PortalSide.class);
    private static final Identifier BLUE_TEXTURE = Identifier.fromNamespaceAndPath(PortalGun.MODID, "textures/vfx/portal_gun_portal_blue.png");
    private static final Identifier ORANGE_TEXTURE = Identifier.fromNamespaceAndPath(PortalGun.MODID, "textures/vfx/portal_gun_portal_orange.png");
    private static final Identifier MODEL = Identifier.fromNamespaceAndPath(PortalGun.MODID, "portal_gun_portal");
    private static final Identifier ANIMATION = Identifier.fromNamespaceAndPath(PortalGun.MODID, "portal_gun_portal");

    @Override
    public void addAdditionalStateData(PortalGunPortalEntity animatable, Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(PORTAL_SIDE, animatable.getPortalSide());
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return renderState.getOrDefaultGeckolibData(PORTAL_SIDE, PortalGunPortalEntity.PortalSide.BLUE) == PortalGunPortalEntity.PortalSide.BLUE ? BLUE_TEXTURE : ORANGE_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(PortalGunPortalEntity animatable) {
        return ANIMATION;
    }
}
