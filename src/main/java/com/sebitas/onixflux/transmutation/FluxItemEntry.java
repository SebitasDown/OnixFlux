package com.sebitas.onixflux.transmutation;

import net.minecraft.world.item.Item;

import java.util.Set;

public record FluxItemEntry(Item item, String displayName, String namespace, String modId, long fxValue, Set<String> categories) {

    public boolean matchesQuery(String query) {
        if (query == null || query.isBlank()) return true;
        String lower = query.toLowerCase();
        return displayName.toLowerCase().contains(lower)
                || namespace.contains(lower)
                || modId.contains(lower);
    }

    public boolean matchesCategory(String categoryId) {
        if (categoryId == null || categoryId.equals("all")) return true;
        return categories.contains(categoryId);
    }

}
