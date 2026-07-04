package com.sebitas.onixflux.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class FluxRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final long cost;
    @Nullable
    private final ItemStack input;

    public FluxRecipe(ResourceLocation id, ItemStack output, long cost, @Nullable ItemStack input) {
        this.id = Objects.requireNonNull(id, "Recipe id cannot be null");
        this.output = output.copy();
        this.cost = cost;
        this.input = input != null ? input.copy() : null;
    }

    public FluxRecipe(ResourceLocation id, ItemStack output, long cost) {
        this(id, output, cost, null);
    }

    public ResourceLocation id() {
        return id;
    }

    public ItemStack output() {
        return output.copy();
    }

    public long cost() {
        return cost;
    }

    @Nullable
    public ItemStack input() {
        return input != null ? input.copy() : null;
    }

    public boolean hasInput() {
        return input != null && !input.isEmpty();
    }

    public boolean hasCost() {
        return cost > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FluxRecipe that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
