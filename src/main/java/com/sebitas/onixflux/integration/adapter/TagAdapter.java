package com.sebitas.onixflux.integration.adapter;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.*;

public interface TagAdapter {

    record TagEntry(TagKey<Item> tag, long fxValue, String source) {}

    default List<TagEntry> extractAll() {
        return List.of();
    }

    default Optional<Long> resolveTag(TagKey<Item> tag) {
        return Optional.empty();
    }

    default Set<TagKey<Item>> supportedTags() {
        return Set.of();
    }

    default boolean handles(TagKey<Item> tag) {
        return false;
    }

}
