package com.sebitas.onixflux.player;

public final class PlayerFlux {

    private long flux;

    public PlayerFlux() {
        this.flux = 0;
    }

    public PlayerFlux(long initial) {
        PlayerValidation.checkFlux(initial);
        this.flux = initial;
    }

    public long get() {
        return flux;
    }

    public void set(long value) {
        PlayerValidation.checkFlux(value);
        this.flux = value;
    }

    public boolean add(long value) {
        if (value <= 0) return false;
        long newValue = PlayerValidation.addWithOverflowCheck(flux, value);
        flux = newValue;
        return true;
    }

    public boolean remove(long value) {
        if (value <= 0) return false;
        if (flux < value) return false;
        flux -= value;
        return true;
    }

    public boolean has() {
        return flux > 0;
    }

    public void clear() {
        flux = 0;
    }

}
