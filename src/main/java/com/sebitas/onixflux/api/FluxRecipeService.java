package com.sebitas.onixflux.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface FluxRecipeService {

    void registerRecipe(FluxRecipe recipe);

    void removeRecipe(ResourceLocation id);

    Optional<FluxRecipe> getRecipe(ResourceLocation id);

    Collection<FluxRecipe> getAllRecipes();

    boolean hasRecipe(ResourceLocation id);

    int recipeCount();

    @Nullable
    FluxRecipe findRecipeByOutput(ItemStack stack);

    void clearRecipes();

}
