package com.sebitas.onixflux.fx.graph;

public record FluxEdge(FluxNode origin, FluxNode destination, int quantity) {

    public FluxEdge {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Edge quantity must be positive: " + quantity);
        }
    }

}
