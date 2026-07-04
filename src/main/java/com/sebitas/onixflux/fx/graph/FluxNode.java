package com.sebitas.onixflux.fx.graph;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public final class FluxNode {

    private final Item item;
    private final List<FluxEdge> outgoing = new ArrayList<>();
    private final List<FluxEdge> incoming = new ArrayList<>();

    public FluxNode(Item item) {
        this.item = item;
    }

    public Item item() {
        return item;
    }

    public List<FluxEdge> outgoing() {
        return List.copyOf(outgoing);
    }

    public List<FluxEdge> incoming() {
        return List.copyOf(incoming);
    }

    void addOutgoing(FluxEdge edge) {
        outgoing.add(edge);
    }

    void addIncoming(FluxEdge edge) {
        incoming.add(edge);
    }

}
