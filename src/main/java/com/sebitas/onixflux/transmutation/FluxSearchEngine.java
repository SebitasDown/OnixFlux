package com.sebitas.onixflux.transmutation;

import com.sebitas.onixflux.fx.FluxEngine;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public final class FluxSearchEngine {

    private FluxSearchEngine() {}

    public static List<FluxItemEntry> buildEntries(Set<Item> learnedItems) {
        List<FluxItemEntry> entries = new ArrayList<>();

        for (Item item : learnedItems) {
            if (item == null) continue;
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            ItemStack stack = new ItemStack(item);
            String displayName = stack.getHoverName().getString();
            long fxValue = FluxEngine.getValue(item).map(v -> v.value()).orElse(0L);
            Set<String> categories = FluxCategoryManager.getCategoriesFor(item);

            entries.add(new FluxItemEntry(
                    item,
                    displayName,
                    id.getNamespace(),
                    id.getNamespace(),
                    fxValue,
                    categories
            ));
        }

        entries.sort(Comparator.comparingLong(FluxItemEntry::fxValue).reversed()
                .thenComparing(FluxItemEntry::displayName));

        return entries;
    }

    public static List<FluxItemEntry> search(List<FluxItemEntry> entries, String query, String categoryId) {
        return entries.stream()
                .filter(entry -> entry.matchesQuery(query))
                .filter(entry -> entry.matchesCategory(categoryId))
                .toList();
    }

    public static List<FluxItemEntry> getPage(List<FluxItemEntry> entries, int page, int itemsPerPage) {
        int fromIndex = page * itemsPerPage;
        if (fromIndex >= entries.size()) return Collections.emptyList();
        int toIndex = Math.min(fromIndex + itemsPerPage, entries.size());
        return entries.subList(fromIndex, toIndex);
    }

    public static int getMaxPage(List<FluxItemEntry> entries, int itemsPerPage) {
        if (entries.isEmpty()) return 1;
        return (int) Math.ceil((double) entries.size() / itemsPerPage);
    }

}
