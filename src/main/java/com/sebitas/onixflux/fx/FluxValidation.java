package com.sebitas.onixflux.fx;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class FluxValidation {

    private FluxValidation() {
    }

    public static void checkItem(Item item) {
        if (item == null) {
            throw new FluxException("Item cannot be null");
        }
        if (item == Items.AIR) {
            throw new FluxException("Item cannot be AIR");
        }
    }

    public static void checkValue(long value) {
        if (value <= 0) {
            throw new FluxException("FX value must be positive: " + value);
        }
    }

    public static void checkSource(FluxSource source) {
        if (source == null) {
            throw new FluxException("FluxSource cannot be null");
        }
    }

    public static void checkNotFrozen(boolean frozen) {
        if (frozen) {
            throw new FluxException("FluxEngine is frozen. Cannot modify registry.");
        }
    }

    public static long addWithOverflowCheck(long a, long b) {
        long result = a + b;
        if (((a ^ result) & (b ^ result)) < 0) {
            throw new FluxException("Long overflow adding FX values: " + a + " + " + b);
        }
        return result;
    }

    public static long multiplyWithOverflowCheck(long a, long b) {
        if (a == 0 || b == 0) return 0;
        long result = a * b;
        if (a == result / b) {
            return result;
        }
        throw new FluxException("Long overflow multiplying FX values: " + a + " * " + b);
    }

}
