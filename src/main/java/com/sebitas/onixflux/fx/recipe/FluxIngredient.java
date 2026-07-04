package com.sebitas.onixflux.fx.recipe;

import net.minecraft.world.item.Item;

public record FluxIngredient(Item item, int count) {

    public FluxIngredient {
        if (count <= 0) {
            throw new IllegalArgumentException("Ingredient count must be positive: " + count);
        }
    }

}
