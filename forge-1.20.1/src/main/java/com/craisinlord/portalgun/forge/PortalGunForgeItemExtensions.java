package com.craisinlord.portalgun.forge;

import com.craisinlord.portalgun.content.client.renderer.AnimatedHeldItemRenderer;
import com.craisinlord.portalgun.content.client.renderer.PortalGunItemRenderers;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

final class PortalGunForgeItemExtensions {
    private PortalGunForgeItemExtensions() {
    }

    static IClientItemExtensions create(PortalGunItem item) {
        return new IClientItemExtensions() {
            private AnimatedHeldItemRenderer<PortalGunItem> renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = PortalGunItemRenderers.create(item);
                }
                return this.renderer;
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        };
    }
}
