package com.sebitas.onixflux.fx.algorithm;

import com.sebitas.onixflux.fx.graph.FluxGraph;
import com.sebitas.onixflux.fx.graph.FluxNode;
import net.minecraft.world.item.Item;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TarjanSCC {

    private final FluxGraph graph;
    private final Map<Item, Integer> index;
    private final Map<Item, Integer> lowlink;
    private final Deque<Item> stack;
    private final Set<Item> onStack;
    private int nextIndex;

    public TarjanSCC(FluxGraph graph) {
        this.graph = graph;
        this.index = new HashMap<>();
        this.lowlink = new HashMap<>();
        this.stack = new ArrayDeque<>();
        this.onStack = new HashSet<>();
        this.nextIndex = 0;
    }

    public Set<Item> findCyclicItems() {
        Set<Item> cyclic = new HashSet<>();

        for (FluxNode node : graph.getAllNodes()) {
            Item v = node.item();
            if (!index.containsKey(v)) {
                strongConnect(v, cyclic);
            }
        }

        return cyclic;
    }

    private void strongConnect(Item v, Set<Item> cyclic) {
        index.put(v, nextIndex);
        lowlink.put(v, nextIndex);
        nextIndex++;
        stack.push(v);
        onStack.add(v);

        for (FluxNode neighbor : graph.getNeighbors(v)) {
            Item w = neighbor.item();
            if (!index.containsKey(w)) {
                strongConnect(w, cyclic);
                lowlink.put(v, Math.min(lowlink.get(v), lowlink.get(w)));
            } else if (onStack.contains(w)) {
                lowlink.put(v, Math.min(lowlink.get(v), index.get(w)));
            }
        }

        if (lowlink.get(v).equals(index.get(v))) {
            List<Item> scc = new ArrayList<>();
            Item w;
            do {
                w = stack.pop();
                onStack.remove(w);
                scc.add(w);
            } while (!w.equals(v));

            if (scc.size() > 1) {
                cyclic.addAll(scc);
            }
        }
    }

}
