package com.sebitas.onixflux.integration.adapter;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public interface ItemAdapter {

    record FluxItemEntry(ResourceLocation id, Item item, long fxValue, String source) {}

    default List<FluxItemEntry> extractAll() {
        return List.of();
    }

    default Optional<Long> getValue(Item item) {
        return Optional.empty();
    }

    default Optional<Long> getValue(ItemStack stack) {
        if (stack.isEmpty()) return Optional.empty();
        return getValue(stack.getItem());
    }

    default boolean handles(Item item) {
        return false;
    }

    default boolean handles(ItemStack stack) {
        return !stack.isEmpty() && handles(stack.getItem());
    }

}
