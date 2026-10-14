package com.craisinlord.portalgun.fabric.client;

import com.craisinlord.portalgun.content.client.renderer.AnimatedHeldItemRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunItemRenderers;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import software.bernie.geckolib.animatable.client.RenderProvider;

public final class PortalGunFabricItemRenderers {
    private PortalGunFabricItemRenderers() {
    }

    public static void register() {
        PortalGunItem.setRenderProviderFactory(item -> new RenderProvider() {
            private AnimatedHeldItemRenderer<PortalGunItem> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = PortalGunItemRenderers.create(item);
                }
                return this.renderer;
            }
        });
    }
}
