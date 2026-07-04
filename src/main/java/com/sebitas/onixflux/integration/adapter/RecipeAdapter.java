package com.sebitas.onixflux.integration.adapter;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.*;

public interface RecipeAdapter {

    record FluxRecipeEntry(ResourceLocation id, ResourceLocation itemId, long fxValue, String source) {}

    default List<FluxRecipeEntry> extractRecipes(RecipeType<?> type) {
        return List.of();
    }

    default List<FluxRecipeEntry> extractAll() {
        return List.of();
    }

    default boolean supports(RecipeType<?> type) {
        return false;
    }

    default Set<RecipeType<?>> supportedTypes() {
        return Set.of();
    }

    static FluxRecipeEntry fromItem(ResourceLocation id, ResourceLocation itemId, long fxValue) {
        return new FluxRecipeEntry(id, itemId, fxValue, "recipe");
    }

}
