package com.sebitas.onixflux.fx;

import com.sebitas.onixflux.fx.algorithm.DependencySolver;
import com.sebitas.onixflux.fx.algorithm.ValuePropagation;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FluxCalculator {

    public void calculate(List<FluxRecipe> recipes, Set<Item> blockedItems) {
        if (recipes.isEmpty()) return;

        ValuePropagation propagation = new ValuePropagation(recipes, blockedItems);
        propagation.propagate();
    }

    public static Map<Item, Long> solveSingleIteration(List<FluxRecipe> recipes, Set<Item> blockedItems) {
        DependencySolver solver = new DependencySolver(recipes, blockedItems);
        return solver.solve();
    }

}
