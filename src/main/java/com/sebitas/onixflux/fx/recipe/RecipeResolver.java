package com.sebitas.onixflux.fx.recipe;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RecipeResolver {

    private final Map<Item, List<FluxRecipe>> recipesByOutput;

    public RecipeResolver(List<FluxRecipe> recipes) {
        this.recipesByOutput = new HashMap<>();
        for (FluxRecipe recipe : recipes) {
            recipesByOutput.computeIfAbsent(recipe.result(), k -> new ArrayList<>()).add(recipe);
        }
    }

    public List<FluxRecipe> getRecipesFor(Item item) {
        return recipesByOutput.getOrDefault(item, List.of());
    }

    public boolean hasRecipeFor(Item item) {
        return recipesByOutput.containsKey(item);
    }

    public int recipeCountFor(Item item) {
        return recipesByOutput.getOrDefault(item, List.of()).size();
    }

    public int totalRecipes() {
        return recipesByOutput.values().stream().mapToInt(List::size).sum();
    }

    public int distinctOutputs() {
        return recipesByOutput.size();
    }

}
