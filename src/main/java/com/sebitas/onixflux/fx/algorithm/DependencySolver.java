package com.sebitas.onixflux.fx.algorithm;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.recipe.FluxIngredient;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DependencySolver {

    private final List<FluxRecipe> recipes;
    private final Set<Item> blockedItems;
    private final MemoizationCache cache;

    public DependencySolver(List<FluxRecipe> recipes, Set<Item> blockedItems) {
        this.recipes = recipes;
        this.blockedItems = new HashSet<>(blockedItems);
        this.cache = new MemoizationCache();
    }

    public Map<Item, Long> solve() {
        Map<Item, Long> candidates = new HashMap<>();

        for (FluxRecipe recipe : recipes) {
            if (!isResolvable(recipe)) continue;

            long inputSum = sumIngredientValues(recipe);
            if (inputSum < 0) continue;

            long value = inputSum / recipe.resultCount();
            if (value <= 0) continue;

            candidates.merge(recipe.result(), value, Math::min);
        }

        return candidates;
    }

    public boolean isResolvable(FluxRecipe recipe) {
        Item result = recipe.result();
        if (FluxEngine.hasValue(result)) return false;
        if (blockedItems.contains(result)) return false;
        return true;
    }

    private long sumIngredientValues(FluxRecipe recipe) {
        long sum = 0;
        for (FluxIngredient ingredient : recipe.ingredients()) {
            Item item = ingredient.item();
            if (blockedItems.contains(item)) return -1;

            var cached = cache.getCached(item);
            if (cached.isPresent()) {
                sum = addToSum(sum, cached.get().value(), ingredient.count());
                continue;
            }

            var fluxValue = FluxEngine.getValue(item);
            if (fluxValue.isEmpty()) return -1;

            cache.cache(item, fluxValue.get());
            sum = addToSum(sum, fluxValue.get().value(), ingredient.count());
        }
        return sum;
    }

    private long addToSum(long sum, long value, int count) {
        long product = value * count;
        if (count > 1 && product / count != value) {
            return -1;
        }
        long result = sum + product;
        if (result < sum) {
            return -1;
        }
        return result;
    }

    public MemoizationCache cache() {
        return cache;
    }

}
