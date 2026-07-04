package com.sebitas.onixflux.fx.provider;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ApiProvider implements FluxValueProvider {

    private static final Map<Item, Long> pendingRegistrations = new LinkedHashMap<>();

    public static void register(Item item, long value) {
        pendingRegistrations.put(item, value);
    }

    @Override
    public void provide() {
        for (var entry : pendingRegistrations.entrySet()) {
            FluxEngine.register(entry.getKey(), entry.getValue(), FluxSource.API);
        }
        pendingRegistrations.clear();
    }

}
