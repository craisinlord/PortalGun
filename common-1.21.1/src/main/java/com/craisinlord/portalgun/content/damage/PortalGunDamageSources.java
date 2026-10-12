package com.craisinlord.portalgun.content.damage;

import com.craisinlord.portalgun.PortalGun;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public final class PortalGunDamageSources {
    public static final ResourceKey<DamageType> MOON_VACUUM =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, "moon_vacuum"));

    private PortalGunDamageSources() {
    }

    public static DamageSource moonVacuum(ServerLevel level, Entity causingEntity) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(MOON_VACUUM), null, causingEntity);
    }
}
