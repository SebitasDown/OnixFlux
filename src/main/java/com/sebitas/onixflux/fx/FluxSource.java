package com.sebitas.onixflux.fx;

public enum FluxSource {

    DEFAULT(0),
    CALCULATED(1),
    API(2),
    CONFIG(3),
    DATAPACK(4);

    private final int priority;

    FluxSource(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

}
