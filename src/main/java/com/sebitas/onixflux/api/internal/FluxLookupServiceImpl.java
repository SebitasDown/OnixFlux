package com.sebitas.onixflux.api.internal;

import com.sebitas.onixflux.api.FluxApiValidation;
import com.sebitas.onixflux.api.FluxLookupService;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import com.sebitas.onixflux.fx.FluxValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Optional;

public final class FluxLookupServiceImpl implements FluxLookupService {

    @Override
    public Optional<FluxValue> getValue(Item item) {
        FluxApiValidation.checkItem(item);
        return FluxEngine.getValue(item);
    }

    @Override
    public long getLongValue(Item item) {
        FluxApiValidation.checkItem(item);
        return FluxEngine.getValue(item).map(FluxValue::value).orElse(0L);
    }

    @Override
    public long getLongValue(ItemStack stack) {
        FluxApiValidation.checkStack(stack);
        return FluxEngine.getValue(stack.getItem()).map(FluxValue::value).orElse(0L);
    }

    @Override
    public boolean hasValue(Item item) {
        FluxApiValidation.checkItem(item);
        return FluxEngine.hasValue(item);
    }

    @Override
    public boolean hasValue(ItemStack stack) {
        FluxApiValidation.checkStack(stack);
        return FluxEngine.hasValue(stack.getItem());
    }

    @Override
    public FluxSource getSource(Item item) {
        FluxApiValidation.checkItem(item);
        return FluxEngine.getValue(item).map(FluxValue::source).orElse(null);
    }

    @Override
    public Map<Item, FluxValue> getAllValues() {
        return FluxEngine.getAllValues();
    }

    @Override
    public int size() {
        return FluxEngine.size();
    }

    @Override
    public boolean isFrozen() {
        return FluxEngine.isFrozen();
    }

}
