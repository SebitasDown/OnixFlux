package com.sebitas.onixflux.fx.graph;

import com.sebitas.onixflux.fx.recipe.FluxIngredient;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import net.minecraft.world.item.Item;

import java.util.List;

public final class GraphBuilder {

    private GraphBuilder() {
    }

    public static FluxGraph fromRecipes(List<FluxRecipe> recipes) {
        FluxGraph graph = new FluxGraph();

        for (FluxRecipe recipe : recipes) {
            Item output = recipe.result();
            for (FluxIngredient ingredient : recipe.ingredients()) {
                graph.connect(output, ingredient.item(), ingredient.count());
            }
        }

        return graph;
    }

}
