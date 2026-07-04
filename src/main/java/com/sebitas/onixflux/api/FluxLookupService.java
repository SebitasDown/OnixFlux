package com.sebitas.onixflux.api;

import com.sebitas.onixflux.fx.FluxSource;
import com.sebitas.onixflux.fx.FluxValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Optional;

public interface FluxLookupService {

    Optional<FluxValue> getValue(Item item);

    long getLongValue(Item item);

    long getLongValue(ItemStack stack);

    boolean hasValue(Item item);

    boolean hasValue(ItemStack stack);

    FluxSource getSource(Item item);

    Map<Item, FluxValue> getAllValues();

    int size();

    boolean isFrozen();

}
