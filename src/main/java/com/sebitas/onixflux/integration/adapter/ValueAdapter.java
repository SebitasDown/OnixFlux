package com.sebitas.onixflux.integration.adapter;

import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface ValueAdapter {

    record ValueEntry(ResourceLocation itemId, long fxValue, String source) {}

    default Optional<Long> getValue(ResourceLocation itemId) {
        return Optional.empty();
    }

    default Optional<ValueEntry> getEntry(ResourceLocation itemId) {
        return getValue(itemId).map(v -> new ValueEntry(itemId, v, "integration"));
    }

    default boolean handles(ResourceLocation itemId) {
        return false;
    }

}
