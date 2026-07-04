package com.sebitas.onixflux.api.internal;

import com.sebitas.onixflux.api.FluxRecipe;
import com.sebitas.onixflux.api.FluxRecipeService;
import com.sebitas.onixflux.api.event.FluxRecipeRegisteredEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class FluxRecipeServiceImpl implements FluxRecipeService {

    private final Map<ResourceLocation, FluxRecipe> recipes = new LinkedHashMap<>();

    @Override
    public void registerRecipe(FluxRecipe recipe) {
        Objects.requireNonNull(recipe, "Recipe cannot be null");
        recipes.put(recipe.id(), recipe);
        MinecraftForge.EVENT_BUS.post(new FluxRecipeRegisteredEvent(recipe));
    }

    @Override
    public void removeRecipe(ResourceLocation id) {
        Objects.requireNonNull(id, "Recipe id cannot be null");
        recipes.remove(id);
    }

    @Override
    public Optional<FluxRecipe> getRecipe(ResourceLocation id) {
        Objects.requireNonNull(id, "Recipe id cannot be null");
        return Optional.ofNullable(recipes.get(id));
    }

    @Override
    public Collection<FluxRecipe> getAllRecipes() {
        return Collections.unmodifiableCollection(new ArrayList<>(recipes.values()));
    }

    @Override
    public boolean hasRecipe(ResourceLocation id) {
        Objects.requireNonNull(id, "Recipe id cannot be null");
        return recipes.containsKey(id);
    }

    @Override
    public int recipeCount() {
        return recipes.size();
    }

    @Override
    @Nullable
    public FluxRecipe findRecipeByOutput(ItemStack stack) {
        Objects.requireNonNull(stack, "ItemStack cannot be null");
        if (stack.isEmpty()) return null;
        for (FluxRecipe recipe : recipes.values()) {
            if (ItemStack.isSameItem(recipe.output(), stack)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public void clearRecipes() {
        recipes.clear();
    }

}
