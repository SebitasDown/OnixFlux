package com.sebitas.onixflux.api.internal;

import com.sebitas.onixflux.api.FluxApiValidation;
import com.sebitas.onixflux.api.FluxRegistrationService;
import com.sebitas.onixflux.api.event.FluxRegisterEvent;
import com.sebitas.onixflux.api.event.FluxRemoveEvent;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import com.sebitas.onixflux.fx.FluxValue;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;

public final class FluxRegistrationServiceImpl implements FluxRegistrationService {

    @Override
    public void register(Item item, long value) {
        register(item, value, FluxSource.API);
    }

    @Override
    public void register(Item item, long value, FluxSource source) {
        FluxApiValidation.checkItem(item);
        FluxApiValidation.checkPositive(value);

        FluxEngine.register(item, value, source);
        MinecraftForge.EVENT_BUS.post(new FluxRegisterEvent(item, new FluxValue(value, source), false));
    }

    @Override
    public void remove(Item item) {
        FluxApiValidation.checkItem(item);

        var existing = FluxEngine.getValue(item);
        FluxEngine.remove(item);
        existing.ifPresent(v -> MinecraftForge.EVENT_BUS.post(new FluxRemoveEvent(item, v)));
    }

    @Override
    public void replace(Item item, long value) {
        FluxApiValidation.checkItem(item);
        FluxApiValidation.checkPositive(value);

        if (FluxEngine.hasValue(item)) {
            FluxEngine.remove(item);
        }
        FluxEngine.register(item, value, FluxSource.API);
        MinecraftForge.EVENT_BUS.post(new FluxRegisterEvent(item, new FluxValue(value, FluxSource.API), true));
    }

    @Override
    public void override(Item item, long value) {
        FluxApiValidation.checkItem(item);
        FluxApiValidation.checkPositive(value);

        if (FluxEngine.hasValue(item)) {
            FluxEngine.remove(item);
        }
        FluxEngine.register(item, value, FluxSource.API);
        MinecraftForge.EVENT_BUS.post(new FluxRegisterEvent(item, new FluxValue(value, FluxSource.API), true));
    }

    @Override
    public void registerAll(Map<Item, Long> values) {
        FluxApiValidation.checkMap(values);
        for (Map.Entry<Item, Long> entry : values.entrySet()) {
            register(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        var all = FluxEngine.getAllValues();
        FluxEngine.clear();
        for (Map.Entry<Item, FluxValue> entry : all.entrySet()) {
            MinecraftForge.EVENT_BUS.post(new FluxRemoveEvent(entry.getKey(), entry.getValue()));
        }
    }

}
