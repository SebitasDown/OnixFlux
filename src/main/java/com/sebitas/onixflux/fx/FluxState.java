package com.sebitas.onixflux.fx;

public enum FluxState {

    UNINITIALIZED,
    BOOTSTRAPPED,
    CONFIG_LOADED,
    RECIPES_CALCULATED,
    FROZEN;

    public boolean isModifiable() {
        return this.ordinal() < FROZEN.ordinal();
    }

    public boolean isAtLeast(FluxState other) {
        return ordinal() >= other.ordinal();
    }

}
