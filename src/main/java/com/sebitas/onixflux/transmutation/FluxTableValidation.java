package com.sebitas.onixflux.transmutation;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class FluxTableValidation {

    private FluxTableValidation() {
    }

    public static void checkItem(Item item) {
        if (item == null) throw new FluxTableException("Item cannot be null");
        if (item == Items.AIR) throw new FluxTableException("Item cannot be AIR");
    }

    public static void checkStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) throw new FluxTableException("ItemStack cannot be empty");
    }

    public static void checkItemKnown(boolean known) {
        if (!known) throw new FluxTableException("Item is not learned");
    }

    public static void checkItemUnknown(boolean known) {
        if (known) throw new FluxTableException("Item is already learned");
    }

    public static void checkSufficientFX(boolean has) {
        if (!has) throw new FluxTableException("Insufficient FX");
    }

    public static void checkHasValue(boolean has) {
        if (!has) throw new FluxTableException("Item has no FX value");
    }

}
