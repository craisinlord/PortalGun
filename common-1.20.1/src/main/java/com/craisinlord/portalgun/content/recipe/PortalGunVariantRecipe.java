package com.craisinlord.portalgun.content.recipe;

import com.craisinlord.portalgun.content.item.PortalGunItem;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class PortalGunVariantRecipe extends ShapelessRecipe {
    public static final RecipeSerializer<PortalGunVariantRecipe> SERIALIZER = new Serializer();

    public PortalGunVariantRecipe(ShapelessRecipe recipe) {
        super(recipe.getId(), recipe.getGroup(), recipe.category(), recipe.getResultItem(RegistryAccess.EMPTY), recipe.getIngredients());
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack result = super.assemble(container, registryAccess);
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack ingredient = container.getItem(slot);
            if (ingredient.getItem() instanceof PortalGunItem) {
                if (ingredient.getTag() != null) {
                    result.setTag(ingredient.getTag().copy());
                }
                result.setDamageValue(ingredient.getDamageValue());
                break;
            }
        }
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static final class Serializer implements RecipeSerializer<PortalGunVariantRecipe> {
        @Override
        public PortalGunVariantRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new PortalGunVariantRecipe(RecipeSerializer.SHAPELESS_RECIPE.fromJson(id, json));
        }

        @Override
        public PortalGunVariantRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new PortalGunVariantRecipe(RecipeSerializer.SHAPELESS_RECIPE.fromNetwork(id, buffer));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PortalGunVariantRecipe recipe) {
            RecipeSerializer.SHAPELESS_RECIPE.toNetwork(buffer, recipe);
        }
    }
}
