package com.craisinlord.portalgun.fabric.mixin.client;

import com.craisinlord.portalgun.content.item.PortalGunItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvatarRenderer.class)
public class CrossbowArmPoseMixin {
    @Inject(method = "getArmPose(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;",
            at = @At("RETURN"), cancellable = true)
    private static void portalgun$portalGunArmPose(Avatar avatar, ItemStack stack, InteractionHand hand,
                                                   CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (stack.getItem() instanceof PortalGunItem) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
