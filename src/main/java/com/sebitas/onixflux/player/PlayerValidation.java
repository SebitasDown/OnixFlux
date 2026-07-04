package com.sebitas.onixflux.player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class PlayerValidation {

    private PlayerValidation() {
    }

    public static void checkFlux(long value) {
        if (value < 0) {
            throw new PlayerException("FX value cannot be negative: " + value);
        }
    }

    public static void checkItem(Item item) {
        if (item == null) {
            throw new PlayerException("Item cannot be null");
        }
        if (item == Items.AIR) {
            throw new PlayerException("Item cannot be AIR");
        }
    }

    public static long addWithOverflowCheck(long a, long b) {
        long result = a + b;
        if (((a ^ result) & (b ^ result)) < 0) {
            throw new PlayerException("FX overflow: " + a + " + " + b);
        }
        return result;
    }

    public static long subtractWithUnderflowCheck(long a, long b) {
        if (a < b) {
            throw new PlayerException("Insufficient FX: " + a + " < " + b);
        }
        return a - b;
    }

}
