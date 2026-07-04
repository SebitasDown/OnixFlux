package com.sebitas.onixflux.fx.graph;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class FluxGraph {

    private final Map<Item, FluxNode> nodes = new HashMap<>();

    public FluxNode getOrCreateNode(Item item) {
        return nodes.computeIfAbsent(item, FluxNode::new);
    }

    public FluxEdge connect(Item from, Item to, int quantity) {
        FluxNode origin = getOrCreateNode(from);
        FluxNode destination = getOrCreateNode(to);
        FluxEdge edge = new FluxEdge(origin, destination, quantity);
        origin.addOutgoing(edge);
        destination.addIncoming(edge);
        return edge;
    }

    public Optional<FluxNode> findNode(Item item) {
        return Optional.ofNullable(nodes.get(item));
    }

    public List<FluxNode> getNeighbors(Item item) {
        FluxNode node = nodes.get(item);
        if (node == null) {
            return List.of();
        }
        List<FluxNode> neighbors = new ArrayList<>();
        for (FluxEdge edge : node.outgoing()) {
            neighbors.add(edge.destination());
        }
        return neighbors;
    }

    public Set<FluxNode> getAllNodes() {
        return Set.copyOf(nodes.values());
    }

    public boolean hasNode(Item item) {
        return nodes.containsKey(item);
    }

    public int nodeCount() {
        return nodes.size();
    }

}
