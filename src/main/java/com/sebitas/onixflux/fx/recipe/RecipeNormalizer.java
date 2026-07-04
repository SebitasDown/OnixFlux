package com.sebitas.onixflux.fx.recipe;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class RecipeNormalizer {

    private RecipeNormalizer() {
    }

    public static List<FluxRecipe> normalize(List<FluxRecipe> recipes) {
        List<FluxRecipe> result = new ArrayList<>();

        for (FluxRecipe recipe : recipes) {
            FluxRecipe normalized = normalizeSingle(recipe);
            if (normalized != null) {
                result.add(normalized);
            }
        }

        return List.copyOf(result);
    }

    private static FluxRecipe normalizeSingle(FluxRecipe recipe) {
        List<FluxIngredient> merged = mergeDuplicates(recipe.ingredients());
        List<FluxIngredient> filtered = removeSelfReferences(recipe.result(), merged);
        List<FluxIngredient> validated = removeInvalid(filtered);

        if (validated.isEmpty()) {
            return null;
        }

        return new FluxRecipe(recipe.result(), recipe.resultCount(), validated);
    }

    static List<FluxIngredient> mergeDuplicates(List<FluxIngredient> ingredients) {
        Map<Item, Integer> merged = new LinkedHashMap<>();

        for (FluxIngredient ingredient : ingredients) {
            merged.merge(ingredient.item(), ingredient.count(), Integer::sum);
        }

        List<FluxIngredient> result = new ArrayList<>();
        for (var entry : merged.entrySet()) {
            result.add(new FluxIngredient(entry.getKey(), entry.getValue()));
        }

        return result;
    }

    static List<FluxIngredient> removeSelfReferences(Item result, List<FluxIngredient> ingredients) {
        List<FluxIngredient> filtered = new ArrayList<>();
        for (FluxIngredient ingredient : ingredients) {
            if (!ingredient.item().equals(result)) {
                filtered.add(ingredient);
            }
        }
        return filtered;
    }

    static List<FluxIngredient> removeInvalid(List<FluxIngredient> ingredients) {
        List<FluxIngredient> valid = new ArrayList<>();
        for (FluxIngredient ingredient : ingredients) {
            if (ingredient.item() != null && ingredient.count() > 0) {
                valid.add(ingredient);
            }
        }
        return valid;
    }

}
