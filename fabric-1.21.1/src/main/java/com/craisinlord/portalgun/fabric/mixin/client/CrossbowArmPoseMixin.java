package com.craisinlord.portalgun.fabric.mixin.client;

import com.craisinlord.portalgun.content.item.PortalGunItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class CrossbowArmPoseMixin {
    @Inject(method = "getArmPose", at = @At("RETURN"), cancellable = true)
    private static void portalgun$portalGunArmPose(AbstractClientPlayer player, InteractionHand hand,
                                                   CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (player.getItemInHand(hand).getItem() instanceof PortalGunItem) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
