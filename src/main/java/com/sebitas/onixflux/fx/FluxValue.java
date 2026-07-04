package com.sebitas.onixflux.fx;

public record FluxValue(long value, FluxSource source) {

    public FluxValue {
        if (value < 0) {
            throw new IllegalArgumentException("FX value cannot be negative: " + value);
        }
    }

    public boolean isCalculated() {
        return source == FluxSource.CALCULATED;
    }

}
