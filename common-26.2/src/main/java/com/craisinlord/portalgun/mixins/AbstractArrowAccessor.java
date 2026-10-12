package com.craisinlord.portalgun.mixins;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Invoker("setInGround")
    void portalgun$setInGround(boolean inGround);

    @Accessor("inGroundTime")
    void portalgun$setInGroundTime(int inGroundTime);

    @Accessor("shakeTime")
    void portalgun$setShakeTime(int shakeTime);
}
