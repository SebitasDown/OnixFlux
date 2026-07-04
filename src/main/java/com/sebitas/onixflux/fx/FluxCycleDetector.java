package com.sebitas.onixflux.fx;

import com.sebitas.onixflux.fx.algorithm.TarjanSCC;
import com.sebitas.onixflux.fx.graph.FluxGraph;
import com.sebitas.onixflux.fx.graph.GraphBuilder;
import com.sebitas.onixflux.fx.graph.TopologicalSorter;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class FluxCycleDetector {

    private FluxGraph graph;
    private Set<Item> cyclicItems;

    public Set<Item> detect(List<FluxRecipe> recipes) {
        this.graph = GraphBuilder.fromRecipes(recipes);
        TarjanSCC tarjan = new TarjanSCC(graph);
        this.cyclicItems = tarjan.findCyclicItems();
        return cyclicItems;
    }

    public Set<Item> cyclicItems() {
        return cyclicItems != null
                ? Collections.unmodifiableSet(cyclicItems)
                : Set.of();
    }

    public boolean hasCycle() {
        if (graph == null) return false;
        return new TopologicalSorter(graph).hasCycle();
    }

    public int cycleCount() {
        return cyclicItems != null ? cyclicItems.size() : 0;
    }

    public FluxGraph graph() {
        return graph;
    }

}
