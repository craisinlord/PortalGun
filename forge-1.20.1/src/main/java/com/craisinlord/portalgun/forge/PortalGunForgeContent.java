package com.craisinlord.portalgun.forge;

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
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class PortalGunForgeContent {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, PortalGun.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, PortalGun.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, PortalGun.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PortalGun.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, PortalGun.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, PortalGun.MODID);
    public static final RegistryObject<PortalGunPortalMasterBlock> PORTAL_MASTER = BLOCKS.register("portal_gun_portal_master",
            () -> new PortalGunPortalMasterBlock(PortalGunForgeContent.PORTAL_MASTER_BE, properties()));
    public static final RegistryObject<PortalGunPortalBaseBlock> PORTAL_BASE = BLOCKS.register("portal_gun_portal_base",
            () -> new PortalGunPortalBaseBlock(PortalGunForgeContent.PORTAL_BASE_BE, properties()));
    public static final RegistryObject<BlockEntityType<PortalGunPortalMasterBlockEntity>> PORTAL_MASTER_BE = BLOCK_ENTITY_TYPES.register("portal_gun_portal_master",
            () -> BlockEntityType.Builder.of((pos, state) -> new PortalGunPortalMasterBlockEntity(pos, state, PortalGunForgeContent.PORTAL_MASTER_BE),
                    PORTAL_MASTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<PortalGunPortalBaseBlockEntity>> PORTAL_BASE_BE = BLOCK_ENTITY_TYPES.register("portal_gun_portal_base",
            () -> BlockEntityType.Builder.of((pos, state) -> new PortalGunPortalBaseBlockEntity(pos, state, PortalGunForgeContent.PORTAL_BASE_BE),
                    PORTAL_BASE.get()).build(null));
    public static final RegistryObject<EntityType<PortalGunPortalEntity>> PORTAL_ENTITY = ENTITIES.register("portal_gun_portal",
            () -> EntityType.Builder.<PortalGunPortalEntity>of(PortalGunPortalEntity::new, MobCategory.MISC)
                    .sized(1.0F, 2.0F).clientTrackingRange(10).updateInterval(1).build("portal_gun_portal"));
    public static final RegistryObject<EntityType<PortalGunProjectileEntity>> PROJECTILE_ENTITY = ENTITIES.register("portal_gun_projectile",
            () -> EntityType.Builder.<PortalGunProjectileEntity>of(PortalGunProjectileEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(8).updateInterval(1).build("portal_gun_projectile"));
    public static final RegistryObject<PortalGunItem> PORTAL_GUN = gun("portal_gun", PortalGunVariant.DEFAULT);
    public static final RegistryObject<PortalGunItem> PORTAL_GUN_ATLAS = gun("portal_gun_atlas", PortalGunVariant.ATLAS);
    public static final RegistryObject<PortalGunItem> PORTAL_GUN_P_BODY = gun("portal_gun_p_body", PortalGunVariant.P_BODY);
    public static final RegistryObject<PortalGunItem> PORTAL_GUN_CRAISIN = gun("portal_gun_craisin", PortalGunVariant.CRAISIN);
    public static final RegistryObject<PortalGunItem> PORTAL_GUN_LAYNCE = gun("portal_gun_laynce", PortalGunVariant.LAYNCE);
    public static final RegistryObject<RecipeSerializer<PortalGunVariantRecipe>> VARIANT_RECIPE =
            RECIPE_SERIALIZERS.register("portal_gun_variant", () -> PortalGunVariantRecipe.SERIALIZER);
    public static final RegistryObject<SoundEvent> FIRE_BLUE = sound("portal_gun_fire_blue");
    public static final RegistryObject<SoundEvent> FIRE_ORANGE = sound("portal_gun_fire_orange");
    public static final RegistryObject<SoundEvent> INVALID_SURFACE = sound("portal_gun_invalid_surface");
    public static final RegistryObject<SoundEvent> OPEN_BLUE = sound("portal_open_blue");
    public static final RegistryObject<SoundEvent> OPEN_ORANGE = sound("portal_open_orange");
    public static final RegistryObject<SoundEvent> FIZZLE = sound("portal_fizzle");
    public static final RegistryObject<SoundEvent> AMBIENT = sound("portal_ambient");
    public static final RegistryObject<SoundEvent> ENTER = sound("portal_enter");
    public static final RegistryObject<SoundEvent> EXIT = sound("portal_exit");

    private PortalGunForgeContent() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        ENTITIES.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        SOUNDS.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
    }

    private static RegistryObject<PortalGunItem> gun(String path, PortalGunVariant variant) {
        return ITEMS.register(path, () -> new PortalGunItem(new Item.Properties().stacksTo(1).durability(256).rarity(Rarity.RARE),
                PORTAL_ENTITY, PROJECTILE_ENTITY, PORTAL_MASTER, PORTAL_BASE, variant) {
            @Override
            public void initializeClient(Consumer<IClientItemExtensions> consumer) {
                consumer.accept(PortalGunForgeItemExtensions.create(this));
            }
        });
    }

    private static RegistryObject<SoundEvent> sound(String path) {
        return SOUNDS.register(path, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(PortalGun.MODID, path)));
    }

    private static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of().strength(-1.0F).noCollission().noOcclusion().lightLevel(state -> 11).noLootTable()
                .isSuffocating((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false);
    }
}
