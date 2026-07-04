package com.sebitas.onixflux.fx.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

public final class FluxRecipeLoader {

    private static final List<RecipeType<?>> SUPPORTED_TYPES = List.of(
            RecipeType.CRAFTING,
            RecipeType.SMELTING,
            RecipeType.BLASTING,
            RecipeType.SMOKING,
            RecipeType.CAMPFIRE_COOKING
    );

    private FluxRecipeLoader() {
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<FluxRecipe> loadAll(RecipeManager recipeManager, RegistryAccess registryAccess) {
        List<FluxRecipe> result = new ArrayList<>();

        for (RecipeType<?> type : SUPPORTED_TYPES) {
            List<Recipe<?>> recipes = (List) recipeManager.getAllRecipesFor((RecipeType) type);
            for (Recipe<?> recipe : recipes) {
                FluxRecipe converted = tryConvert(recipe, registryAccess);
                if (converted != null) {
                    result.add(converted);
                }
            }
        }

        return List.copyOf(result);
    }

    private static FluxRecipe tryConvert(Recipe<?> recipe, RegistryAccess registryAccess) {
        if (recipe.isSpecial()) {
            return null;
        }

        ItemStack output = recipe.getResultItem(registryAccess);
        if (output.isEmpty()) {
            return null;
        }

        List<FluxIngredient> ingredients = new ArrayList<>();

        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.isEmpty()) {
                continue;
            }

            ItemStack[] stacks = ingredient.getItems();
            if (stacks == null || stacks.length == 0) {
                continue;
            }

            ItemStack firstStack = stacks[0];
            if (firstStack.isEmpty()) {
                continue;
            }

            ingredients.add(new FluxIngredient(firstStack.getItem(), firstStack.getCount()));
        }

        if (ingredients.isEmpty()) {
            return null;
        }

        return new FluxRecipe(output.getItem(), output.getCount(), ingredients);
    }

}
