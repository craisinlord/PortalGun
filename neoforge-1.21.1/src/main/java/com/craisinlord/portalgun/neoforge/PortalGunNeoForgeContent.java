package com.craisinlord.portalgun.neoforge;

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
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PortalGunNeoForgeContent {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PortalGun.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PortalGun.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, PortalGun.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PortalGun.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, PortalGun.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, PortalGun.MODID);
    public static final DeferredBlock<PortalGunPortalMasterBlock> PORTAL_MASTER = BLOCKS.register("portal_gun_portal_master", () -> new PortalGunPortalMasterBlock(PortalGunNeoForgeContent.PORTAL_MASTER_BE::get, properties()));
    public static final DeferredBlock<PortalGunPortalBaseBlock> PORTAL_BASE = BLOCKS.register("portal_gun_portal_base", () -> new PortalGunPortalBaseBlock(PortalGunNeoForgeContent.PORTAL_BASE_BE::get, properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PortalGunPortalMasterBlockEntity>> PORTAL_MASTER_BE = BLOCK_ENTITY_TYPES.register("portal_gun_portal_master", () -> BlockEntityType.Builder.of(
            (pos, state) -> new PortalGunPortalMasterBlockEntity(pos, state, PortalGunNeoForgeContent.PORTAL_MASTER_BE::get), PORTAL_MASTER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PortalGunPortalBaseBlockEntity>> PORTAL_BASE_BE = BLOCK_ENTITY_TYPES.register("portal_gun_portal_base", () -> BlockEntityType.Builder.of(
            (pos, state) -> new PortalGunPortalBaseBlockEntity(pos, state, PortalGunNeoForgeContent.PORTAL_BASE_BE::get), PORTAL_BASE.get()).build(null));
    public static final DeferredHolder<EntityType<?>, EntityType<PortalGunPortalEntity>> PORTAL_ENTITY = ENTITIES.register("portal_gun_portal", () -> EntityType.Builder.<PortalGunPortalEntity>of(PortalGunPortalEntity::new, MobCategory.MISC)
            .sized(1.0F, 2.0F).clientTrackingRange(10).updateInterval(1).build("portal_gun_portal"));
    public static final DeferredHolder<EntityType<?>, EntityType<PortalGunProjectileEntity>> PROJECTILE_ENTITY = ENTITIES.register("portal_gun_projectile", () -> EntityType.Builder.<PortalGunProjectileEntity>of(PortalGunProjectileEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F).clientTrackingRange(8).updateInterval(1).build("portal_gun_projectile"));
    public static final DeferredItem<PortalGunItem> PORTAL_GUN = gun("portal_gun", PortalGunVariant.DEFAULT);
    public static final DeferredItem<PortalGunItem> PORTAL_GUN_ATLAS = gun("portal_gun_atlas", PortalGunVariant.ATLAS);
    public static final DeferredItem<PortalGunItem> PORTAL_GUN_P_BODY = gun("portal_gun_p_body", PortalGunVariant.P_BODY);
    public static final DeferredItem<PortalGunItem> PORTAL_GUN_CRAISIN = gun("portal_gun_craisin", PortalGunVariant.CRAISIN);
    public static final DeferredItem<PortalGunItem> PORTAL_GUN_LAYNCE = gun("portal_gun_laynce", PortalGunVariant.LAYNCE);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PortalGunVariantRecipe>> VARIANT_RECIPE = RECIPE_SERIALIZERS.register("portal_gun_variant", () -> (RecipeSerializer<PortalGunVariantRecipe>) PortalGunVariantRecipe.SERIALIZER);
    public static final DeferredHolder<SoundEvent, SoundEvent> FIRE_BLUE = sound("portal_gun_fire_blue");
    public static final DeferredHolder<SoundEvent, SoundEvent> FIRE_ORANGE = sound("portal_gun_fire_orange");
    public static final DeferredHolder<SoundEvent, SoundEvent> INVALID_SURFACE = sound("portal_gun_invalid_surface");
    public static final DeferredHolder<SoundEvent, SoundEvent> OPEN_BLUE = sound("portal_open_blue");
    public static final DeferredHolder<SoundEvent, SoundEvent> OPEN_ORANGE = sound("portal_open_orange");
    public static final DeferredHolder<SoundEvent, SoundEvent> FIZZLE = sound("portal_fizzle");
    public static final DeferredHolder<SoundEvent, SoundEvent> AMBIENT = sound("portal_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTER = sound("portal_enter");
    public static final DeferredHolder<SoundEvent, SoundEvent> EXIT = sound("portal_exit");

    private PortalGunNeoForgeContent() { }
    private static DeferredItem<PortalGunItem> gun(String path, PortalGunVariant variant) {
        return ITEMS.register(path, () -> new PortalGunItem(new Item.Properties().stacksTo(1).durability(256).rarity(Rarity.RARE), PORTAL_ENTITY, PROJECTILE_ENTITY, PORTAL_MASTER, PORTAL_BASE, variant));
    }
    private static DeferredHolder<SoundEvent, SoundEvent> sound(String path) {
        return SOUNDS.register(path, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PortalGun.MODID, path)));
    }
    private static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of().strength(-1.0F).noCollission().noOcclusion().lightLevel(state -> 11).noLootTable()
                .isSuffocating((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false);
    }
}
