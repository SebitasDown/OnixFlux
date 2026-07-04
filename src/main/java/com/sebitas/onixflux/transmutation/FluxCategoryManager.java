package com.sebitas.onixflux.transmutation;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public final class FluxCategoryManager {

    private static final Map<String, FluxCategory> CATEGORIES = new LinkedHashMap<>();
    private static boolean initialized = false;

    public record FluxCategory(String id, Component displayName, Predicate<Item> filter) {}

    private FluxCategoryManager() {}

    public static void initialize() {
        if (initialized) return;

        register("all", Component.translatable("category.onixflux.all"), item -> true);
        register("blocks", Component.translatable("category.onixflux.blocks"),
                item -> item instanceof BlockItem);
        register("tools", Component.translatable("category.onixflux.tools"),
                item -> item instanceof TieredItem || item instanceof SwordItem || item instanceof DiggerItem);
        register("weapons", Component.translatable("category.onixflux.weapons"),
                item -> item instanceof SwordItem || item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem);
        register("armor", Component.translatable("category.onixflux.armor"),
                item -> item instanceof ArmorItem);
        register("food", Component.translatable("category.onixflux.food"),
                item -> item instanceof ItemNameBlockItem || item.getFoodProperties() != null);
        register("ores", Component.translatable("category.onixflux.ores"),
                item -> {
                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                    return id.getPath().contains("ore") || id.getPath().contains("raw_") || id.getPath().contains("ingot") || id.getPath().contains("gem");
                });
        register("redstone", Component.translatable("category.onixflux.redstone"),
                item -> {
                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                    return id.getPath().contains("redstone") || id.getPath().contains("comparator") || id.getPath().contains("repeater") || id.getPath().contains("piston") || item == Items.REDSTONE;
                });
        register("nature", Component.translatable("category.onixflux.nature"),
                item -> item instanceof BlockItem || item instanceof SpawnEggItem || item instanceof BoatItem);
        register("misc", Component.translatable("category.onixflux.misc"),
                item -> true);

        initialized = true;
    }

    public static void register(String id, Component displayName, Predicate<Item> filter) {
        CATEGORIES.put(id, new FluxCategory(id, displayName, filter));
    }

    public static Collection<FluxCategory> getCategories() {
        return CATEGORIES.values();
    }

    public static List<FluxCategory> getVisibleCategories() {
        return CATEGORIES.values().stream().toList();
    }

    @Nullable
    public static FluxCategory getCategory(String id) {
        return CATEGORIES.get(id);
    }

    public static Set<String> getCategoriesFor(Item item) {
        Set<String> result = new HashSet<>();
        for (Map.Entry<String, FluxCategory> entry : CATEGORIES.entrySet()) {
            if (!entry.getKey().equals("all") && !entry.getKey().equals("misc") && entry.getValue().filter().test(item)) {
                result.add(entry.getKey());
            }
        }
        if (result.isEmpty()) result.add("misc");
        return result;
    }

    public static List<Item> filterByCategory(List<Item> items, String categoryId) {
        FluxCategory category = CATEGORIES.get(categoryId);
        if (category == null || categoryId.equals("all")) return items;
        return items.stream().filter(category.filter()).toList();
    }

}
