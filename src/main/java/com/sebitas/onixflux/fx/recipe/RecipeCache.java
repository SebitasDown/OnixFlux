package com.sebitas.onixflux.fx.recipe;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class RecipeCache {

    private final Map<Item, List<FluxRecipe>> recipesByOutput;
    private final Map<Item, Long> costCache;
    private final Map<Item, Boolean> resolvableCache;

    public RecipeCache(List<FluxRecipe> recipes) {
        this.recipesByOutput = new HashMap<>();
        this.costCache = new HashMap<>();
        this.resolvableCache = new HashMap<>();

        for (FluxRecipe recipe : recipes) {
            recipesByOutput.computeIfAbsent(recipe.result(), k -> new java.util.ArrayList<>()).add(recipe);
        }
    }

    public List<FluxRecipe> getRecipesFor(Item item) {
        return recipesByOutput.getOrDefault(item, List.of());
    }

    public void cacheCost(Item item, long cost) {
        costCache.put(item, cost);
    }

    public Optional<Long> getCachedCost(Item item) {
        return Optional.ofNullable(costCache.get(item));
    }

    public boolean hasCachedCost(Item item) {
        return costCache.containsKey(item);
    }

    public void cacheResolvable(Item item, boolean resolvable) {
        resolvableCache.put(item, resolvable);
    }

    public boolean isCachedResolvable(Item item) {
        return resolvableCache.getOrDefault(item, false);
    }

    public void clear() {
        costCache.clear();
        resolvableCache.clear();
    }

}
