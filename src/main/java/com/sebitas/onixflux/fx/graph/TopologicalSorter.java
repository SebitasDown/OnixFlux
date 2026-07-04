package com.sebitas.onixflux.fx.graph;

import net.minecraft.world.item.Item;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TopologicalSorter {

    private final FluxGraph graph;

    public TopologicalSorter(FluxGraph graph) {
        this.graph = graph;
    }

    public List<Item> sort() {
        Map<Item, Integer> inDegree = new HashMap<>();

        for (FluxNode node : graph.getAllNodes()) {
            inDegree.putIfAbsent(node.item(), 0);
            for (FluxEdge edge : node.outgoing()) {
                Item dest = edge.destination().item();
                inDegree.merge(dest, 1, Integer::sum);
            }
        }

        Deque<Item> queue = new ArrayDeque<>();
        for (var entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Item> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            Item current = queue.poll();
            sorted.add(current);

            FluxNode node = graph.findNode(current).orElse(null);
            if (node == null) continue;

            for (FluxEdge edge : node.outgoing()) {
                Item dest = edge.destination().item();
                int newDegree = inDegree.merge(dest, -1, Integer::sum);
                if (newDegree == 0) {
                    queue.add(dest);
                }
            }
        }

        return List.copyOf(sorted);
    }

    public boolean hasCycle() {
        return sort().size() < graph.nodeCount();
    }

}
