package com.craisinlord.portalgun.mixins;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Accessor("inGround")
    void portalgun$setInGround(boolean inGround);

    @Accessor("inGroundTime")
    void portalgun$setInGroundTime(int inGroundTime);

    @Accessor("shakeTime")
    void portalgun$setShakeTime(int shakeTime);
}
