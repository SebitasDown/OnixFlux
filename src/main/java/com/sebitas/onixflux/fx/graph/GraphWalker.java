package com.sebitas.onixflux.fx.graph;

import net.minecraft.world.item.Item;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final class GraphWalker {

    private final FluxGraph graph;

    public GraphWalker(FluxGraph graph) {
        this.graph = graph;
    }

    public void bfs(Item start, Consumer<FluxNode> visitor) {
        Set<Item> visited = new HashSet<>();
        Deque<FluxNode> queue = new ArrayDeque<>();

        graph.findNode(start).ifPresent(queue::add);

        while (!queue.isEmpty()) {
            FluxNode current = queue.poll();
            if (!visited.add(current.item())) continue;

            visitor.accept(current);

            for (FluxNode neighbor : graph.getNeighbors(current.item())) {
                if (!visited.contains(neighbor.item())) {
                    queue.add(neighbor);
                }
            }
        }
    }

    public void dfs(Item start, Consumer<FluxNode> visitor) {
        Set<Item> visited = new HashSet<>();
        Deque<FluxNode> stack = new ArrayDeque<>();

        graph.findNode(start).ifPresent(stack::push);

        while (!stack.isEmpty()) {
            FluxNode current = stack.pop();
            if (!visited.add(current.item())) continue;

            visitor.accept(current);

            for (FluxNode neighbor : graph.getNeighbors(current.item())) {
                if (!visited.contains(neighbor.item())) {
                    stack.push(neighbor);
                }
            }
        }
    }

    public Set<FluxNode> reachableNodes(Item start) {
        Set<FluxNode> result = new LinkedHashSet<>();
        bfs(start, result::add);
        return result;
    }

    public int distance(Item from, Item to) {
        Set<Item> visited = new HashSet<>();
        Deque<Item> queue = new ArrayDeque<>();
        Deque<Integer> distances = new ArrayDeque<>();

        queue.add(from);
        distances.add(0);
        visited.add(from);

        while (!queue.isEmpty()) {
            Item current = queue.poll();
            int dist = distances.poll();

            if (current.equals(to)) return dist;

            for (FluxNode neighbor : graph.getNeighbors(current)) {
                if (visited.add(neighbor.item())) {
                    queue.add(neighbor.item());
                    distances.add(dist + 1);
                }
            }
        }

        return -1;
    }

    public List<List<FluxNode>> findLevels(Item start) {
        List<List<FluxNode>> levels = new ArrayList<>();
        Set<Item> visited = new HashSet<>();
        Deque<FluxNode> queue = new ArrayDeque<>();

        graph.findNode(start).ifPresent(n -> {
            queue.add(n);
            visited.add(n.item());
        });

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<FluxNode> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                FluxNode current = queue.poll();
                level.add(current);

                for (FluxNode neighbor : graph.getNeighbors(current.item())) {
                    if (visited.add(neighbor.item())) {
                        queue.add(neighbor);
                    }
                }
            }

            levels.add(level);
        }

        return levels;
    }

}
