package com.craisinlord.portalgun.mixins.client;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityPortalLerpAccessor {
    @Mutable
    @Accessor("lerpSteps")
    void portalgun$setLerpSteps(int steps);

    @Mutable
    @Accessor("lerpHeadSteps")
    void portalgun$setLerpHeadSteps(int steps);
}
