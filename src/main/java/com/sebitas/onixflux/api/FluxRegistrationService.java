package com.sebitas.onixflux.api;

import net.minecraft.world.item.Item;

import java.util.Map;

public interface FluxRegistrationService {

    void register(Item item, long value);

    void register(Item item, long value, com.sebitas.onixflux.fx.FluxSource source);

    void remove(Item item);

    void replace(Item item, long value);

    void override(Item item, long value);

    void registerAll(Map<Item, Long> values);

    void clear();

}
