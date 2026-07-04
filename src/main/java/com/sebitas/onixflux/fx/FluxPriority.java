package com.sebitas.onixflux.fx;

public record FluxPriority(int value) implements Comparable<FluxPriority> {

    public static final FluxPriority VANILLA = new FluxPriority(0);
    public static final FluxPriority CALCULATED = new FluxPriority(1);
    public static final FluxPriority API = new FluxPriority(2);
    public static final FluxPriority CONFIG = new FluxPriority(3);

    public FluxPriority {
        if (value < 0) {
            throw new IllegalArgumentException("Priority cannot be negative: " + value);
        }
    }

    public boolean isHigherOrEqual(FluxPriority other) {
        return value >= other.value;
    }

    @Override
    public int compareTo(FluxPriority other) {
        return Integer.compare(value, other.value);
    }

}
