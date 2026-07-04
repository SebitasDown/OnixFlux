package com.sebitas.onixflux.fx.algorithm;

import com.sebitas.onixflux.fx.FluxValue;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class MemoizationCache {

    private final Map<Item, Optional<FluxValue>> valueCache;

    public MemoizationCache() {
        this.valueCache = new HashMap<>();
    }

    public void cache(Item item, FluxValue value) {
        valueCache.put(item, Optional.of(value));
    }

    public Optional<FluxValue> getCached(Item item) {
        return valueCache.getOrDefault(item, Optional.empty());
    }

    public boolean hasCached(Item item) {
        return valueCache.containsKey(item) && valueCache.get(item).isPresent();
    }

    public void invalidate(Item item) {
        valueCache.remove(item);
    }

    public void clear() {
        valueCache.clear();
    }

    public int size() {
        return (int) valueCache.values().stream().filter(Optional::isPresent).count();
    }

}
