package com.sebitas.onixflux.fx.provider;

import com.sebitas.onixflux.fx.FluxCycleDetector;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.algorithm.ValuePropagation;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Set;

public final class RecipeProvider implements FluxValueProvider {

    private final List<FluxRecipe> recipes;

    public RecipeProvider(List<FluxRecipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public void provide() {
        FluxEngine.removeCalculatedValues();

        Set<Item> cyclic = new FluxCycleDetector().detect(recipes);
        if (!cyclic.isEmpty()) {
            FluxEngine.diagnostics().recordCycles(cyclic.size());
        }

        ValuePropagation propagation = new ValuePropagation(recipes, cyclic);
        int calculated = propagation.propagate();

        if (calculated > 0) {
            FluxEngine.diagnostics().recordCalculated(calculated);
        }
    }

}
