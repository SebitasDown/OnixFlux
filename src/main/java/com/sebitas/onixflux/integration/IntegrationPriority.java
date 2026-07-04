package com.sebitas.onixflux.integration;

public enum IntegrationPriority {

    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4);

    private final int value;

    IntegrationPriority(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static IntegrationPriority fromValue(int value) {
        for (var p : values()) {
            if (p.value == value) return p;
        }
        return NORMAL;
    }

}
