package com.sebitas.onixflux.fx.recipe;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RecipeAnalyzer {

    private final List<FluxRecipe> recipes;
    private final Map<Item, Integer> usageCount;

    public RecipeAnalyzer(List<FluxRecipe> recipes) {
        this.recipes = recipes;
        this.usageCount = new HashMap<>();
        buildUsageCount();
    }

    private void buildUsageCount() {
        for (FluxRecipe recipe : recipes) {
            for (FluxIngredient ingredient : recipe.ingredients()) {
                usageCount.merge(ingredient.item(), 1, Integer::sum);
            }
        }
    }

    public int totalRecipes() {
        return recipes.size();
    }

    public Set<Item> allOutputs() {
        Set<Item> outputs = new HashSet<>();
        for (FluxRecipe recipe : recipes) {
            outputs.add(recipe.result());
        }
        return outputs;
    }

    public Set<Item> allIngredients() {
        Set<Item> ingredients = new HashSet<>();
        for (FluxRecipe recipe : recipes) {
            for (FluxIngredient ingredient : recipe.ingredients()) {
                ingredients.add(ingredient.item());
            }
        }
        return ingredients;
    }

    public int ingredientUsageCount(Item item) {
        return usageCount.getOrDefault(item, 0);
    }

    public boolean isIngredient(Item item) {
        return usageCount.containsKey(item);
    }

    public long totalIngredientCount() {
        return recipes.stream()
                .flatMap(r -> r.ingredients().stream())
                .count();
    }

    public double averageIngredientsPerRecipe() {
        if (recipes.isEmpty()) return 0;
        return (double) totalIngredientCount() / recipes.size();
    }

}
