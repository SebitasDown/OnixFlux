package com.sebitas.onixflux.fx.recipe;

import net.minecraft.world.item.Item;

import java.util.List;

public record FluxRecipe(Item result, int resultCount, List<FluxIngredient> ingredients) {

    public FluxRecipe {
        if (resultCount <= 0) {
            throw new IllegalArgumentException("Result count must be positive: " + resultCount);
        }
        ingredients = List.copyOf(ingredients);
    }

    public boolean hasIngredient(Item item) {
        for (var ingredient : ingredients) {
            if (ingredient.item().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public int getIngredientCount(Item item) {
        for (var ingredient : ingredients) {
            if (ingredient.item().equals(item)) {
                return ingredient.count();
            }
        }
        return 0;
    }

}
