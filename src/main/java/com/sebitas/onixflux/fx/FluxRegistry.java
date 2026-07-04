package com.sebitas.onixflux.fx;

import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class FluxRegistry {

    private final Map<Item, FluxValue> values;

    public FluxRegistry() {
        this.values = new HashMap<>();
    }

    public void register(Item item, FluxValue value) {
        Objects.requireNonNull(item, "Item must not be null");
        Objects.requireNonNull(value, "FluxValue must not be null");

        FluxValue existing = values.get(item);
        if (existing == null || value.source().priority() >= existing.source().priority()) {
            values.put(item, value);
        }
    }

    public void forceRegister(Item item, FluxValue value) {
        Objects.requireNonNull(item, "Item must not be null");
        Objects.requireNonNull(value, "FluxValue must not be null");
        values.put(item, value);
    }

    public Optional<FluxValue> get(Item item) {
        Objects.requireNonNull(item, "Item must not be null");
        return Optional.ofNullable(values.get(item));
    }

    public boolean contains(Item item) {
        Objects.requireNonNull(item, "Item must not be null");
        return values.containsKey(item);
    }

    public void remove(Item item) {
        Objects.requireNonNull(item, "Item must not be null");
        values.remove(item);
    }

    public void removeAll(Set<Item> items) {
        items.forEach(values::remove);
    }

    public void removeCalculated() {
        values.entrySet().removeIf(entry -> entry.getValue().isCalculated());
    }

    public void clear() {
        values.clear();
    }

    public Map<Item, FluxValue> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(values));
    }

    public Map<Item, FluxValue> getBySource(FluxSource source) {
        Map<Item, FluxValue> filtered = new HashMap<>();
        for (var entry : values.entrySet()) {
            if (entry.getValue().source() == source) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return Collections.unmodifiableMap(filtered);
    }

    public int countBySource(FluxSource source) {
        return (int) values.values().stream()
                .filter(v -> v.source() == source)
                .count();
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

}
