package com.sebitas.onixflux.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;
import java.util.Objects;

public final class FluxApiValidation {

    private FluxApiValidation() {}

    public static void checkItem(Item item) {
        if (item == null) throw new FluxApiException("Item cannot be null");
        if (item == Items.AIR) throw new FluxApiException("Item cannot be AIR");
    }

    public static void checkStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) throw new FluxApiException("ItemStack cannot be null or empty");
    }

    public static void checkPlayer(Player player) {
        if (player == null) throw new FluxApiException("Player cannot be null");
    }

    public static void checkPositive(long value) {
        if (value <= 0) throw new FluxApiException("Value must be positive: " + value);
    }

    public static void checkNonNegative(long value) {
        if (value < 0) throw new FluxApiException("Value cannot be negative: " + value);
    }

    public static void checkNotOverflow(long current, long add) {
        if (add > 0 && current > FluxApiConstants.MAX_FLUX - add) {
            throw new FluxApiException("FX value overflow");
        }
    }

    public static void checkNotOverflowSubtract(long current, long sub) {
        if (current < sub) {
            throw new FluxApiException("Insufficient FX: " + current + " < " + sub);
        }
    }

    public static void checkMap(Map<?, ?> map) {
        Objects.requireNonNull(map, "Map cannot be null");
    }

}
