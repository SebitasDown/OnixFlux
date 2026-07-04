package com.sebitas.onixflux.fx.algorithm;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import com.sebitas.onixflux.fx.FluxValidation;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ValuePropagation {

    private final List<FluxRecipe> recipes;
    private final Set<Item> blockedItems;
    private int valuesCalculated;

    public ValuePropagation(List<FluxRecipe> recipes, Set<Item> blockedItems) {
        this.recipes = recipes;
        this.blockedItems = new HashSet<>(blockedItems);
        this.valuesCalculated = 0;
    }

    public int propagate() {
        if (recipes.isEmpty()) return 0;

        boolean changed;
        do {
            changed = false;
            DependencySolver solver = new DependencySolver(recipes, blockedItems);
            Map<Item, Long> candidates = solver.solve();

            if (!candidates.isEmpty()) {
                changed = true;
                for (var entry : candidates.entrySet()) {
                    registerValue(entry.getKey(), entry.getValue());
                }
            }
        } while (changed);

        return valuesCalculated;
    }

    private void registerValue(Item item, long value) {
        FluxValidation.checkValue(value);
        if (!FluxEngine.hasValue(item)) {
            FluxEngine.register(item, value, FluxSource.CALCULATED);
            valuesCalculated++;
        }
    }

    public int valuesCalculated() {
        return valuesCalculated;
    }

}
