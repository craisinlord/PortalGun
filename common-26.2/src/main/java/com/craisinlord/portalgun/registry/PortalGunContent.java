package com.craisinlord.portalgun.registry;

import com.craisinlord.portalgun.PortalGun;
import com.craisinlord.portalgun.content.block.PortalGunPortalBaseBlock;
import com.craisinlord.portalgun.content.block.PortalGunPortalMasterBlock;
import com.craisinlord.portalgun.content.block.entity.PortalGunPortalBaseBlockEntity;
import com.craisinlord.portalgun.content.block.entity.PortalGunPortalMasterBlockEntity;
import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.craisinlord.portalgun.content.portalgun.PortalGunPortalEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunProjectileEntity;
import com.craisinlord.portalgun.content.portalgun.PortalGunVariant;
import com.craisinlord.portalgun.content.recipe.PortalGunVariantRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.crafting.RecipeSerializer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;

public final class PortalGunContent {
    private static boolean registered;
    public static Block PORTAL_MASTER;
    public static Block PORTAL_BASE;
    public static BlockEntityType<PortalGunPortalMasterBlockEntity> PORTAL_MASTER_BE;
    public static BlockEntityType<PortalGunPortalBaseBlockEntity> PORTAL_BASE_BE;
    public static EntityType<PortalGunPortalEntity> PORTAL_ENTITY;
    public static EntityType<PortalGunProjectileEntity> PROJECTILE_ENTITY;
    public static Item PORTAL_GUN;
    public static Item PORTAL_GUN_ATLAS;
    public static Item PORTAL_GUN_P_BODY;
    public static Item PORTAL_GUN_CRAISIN;
    public static Item PORTAL_GUN_LAYNCE;
    public static RecipeSerializer<?> VARIANT_RECIPE;
    public static SoundEvent FIRE_BLUE;
    public static SoundEvent FIRE_ORANGE;
    public static SoundEvent INVALID_SURFACE;
    public static SoundEvent OPEN_BLUE;
    public static SoundEvent OPEN_ORANGE;
    public static SoundEvent FIZZLE;
    public static SoundEvent AMBIENT;
    public static SoundEvent ENTER;
    public static SoundEvent EXIT;

    private PortalGunContent() { }

    public static synchronized void register(BlockEntityTypeFactory blockEntityTypes) {
        if (registered) return;
        registered = true;
        PORTAL_MASTER = Registry.register(BuiltInRegistries.BLOCK, id("portal_gun_portal_master"),
                new PortalGunPortalMasterBlock(() -> PORTAL_MASTER_BE, portalProperties("portal_gun_portal_master")));
        PORTAL_BASE = Registry.register(BuiltInRegistries.BLOCK, id("portal_gun_portal_base"),
                new PortalGunPortalBaseBlock(() -> PORTAL_BASE_BE, portalProperties("portal_gun_portal_base")));
        PORTAL_MASTER_BE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("portal_gun_portal_master"),
                blockEntityTypes.create((pos, state) -> new PortalGunPortalMasterBlockEntity(pos, state, () -> PORTAL_MASTER_BE), PORTAL_MASTER));
        PORTAL_BASE_BE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("portal_gun_portal_base"),
                blockEntityTypes.create((pos, state) -> new PortalGunPortalBaseBlockEntity(pos, state, () -> PORTAL_BASE_BE), PORTAL_BASE));
        PORTAL_ENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE, id("portal_gun_portal"),
                EntityType.Builder.<PortalGunPortalEntity>of(PortalGunPortalEntity::new, MobCategory.MISC)
                        .sized(1.0F, 2.0F).clientTrackingRange(10).updateInterval(1).build(ResourceKey.create(Registries.ENTITY_TYPE, id("portal_gun_portal"))));
        PROJECTILE_ENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE, id("portal_gun_projectile"),
                EntityType.Builder.<PortalGunProjectileEntity>of(PortalGunProjectileEntity::new, MobCategory.MISC)
                        .sized(0.25F, 0.25F).clientTrackingRange(8).updateInterval(1).build(ResourceKey.create(Registries.ENTITY_TYPE, id("portal_gun_projectile"))));
        PORTAL_GUN = item("portal_gun", PortalGunVariant.DEFAULT);
        PORTAL_GUN_ATLAS = item("portal_gun_atlas", PortalGunVariant.ATLAS);
        PORTAL_GUN_P_BODY = item("portal_gun_p_body", PortalGunVariant.P_BODY);
        PORTAL_GUN_CRAISIN = item("portal_gun_craisin", PortalGunVariant.CRAISIN);
        PORTAL_GUN_LAYNCE = item("portal_gun_laynce", PortalGunVariant.LAYNCE);
        VARIANT_RECIPE = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id("portal_gun_variant"), PortalGunVariantRecipe.SERIALIZER);
        Registry.register(BuiltInRegistries.TICKET_TYPE, id("portal_gun_projectile"), PortalGunProjectileEntity.PROJECTILE_TICKET);
        FIRE_BLUE = sound("portal_gun_fire_blue");
        FIRE_ORANGE = sound("portal_gun_fire_orange");
        INVALID_SURFACE = sound("portal_gun_invalid_surface");
        OPEN_BLUE = sound("portal_open_blue");
        OPEN_ORANGE = sound("portal_open_orange");
        FIZZLE = sound("portal_fizzle");
        AMBIENT = sound("portal_ambient");
        ENTER = sound("portal_enter");
        EXIT = sound("portal_exit");
        PortalGun.LOGGER.info("Registered Portal Gun content");
    }

    private static Item item(String name, PortalGunVariant variant) {
        return Registry.register(BuiltInRegistries.ITEM, id(name), new PortalGunItem(
                new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id(name))).stacksTo(1).durability(256).rarity(Rarity.RARE), () -> PORTAL_ENTITY, () -> PROJECTILE_ENTITY,
                () -> PORTAL_MASTER, () -> PORTAL_BASE, variant));
    }

    private static SoundEvent sound(String name) {
        Identifier id = id(name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    private static BlockBehaviour.Properties portalProperties(String name) {
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id(name))).strength(-1.0F).noCollision().noOcclusion().lightLevel(state -> 11).noLootTable()
                .isSuffocating((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false);
    }

    private static Identifier id(String path) { return Identifier.fromNamespaceAndPath(PortalGun.MODID, path); }

    public interface BlockEntityTypeFactory {
        <T extends BlockEntity> BlockEntityType<T> create(BiFunction<BlockPos, BlockState, T> factory, Block block);
    }
}
