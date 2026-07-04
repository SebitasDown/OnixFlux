package com.sebitas.onixflux.api.event;

import com.sebitas.onixflux.fx.FluxValue;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class FluxRemoveEvent extends Event {

    private final Item item;
    private final FluxValue value;

    public FluxRemoveEvent(Item item, FluxValue value) {
        this.item = item;
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public FluxValue getValue() {
        return value;
    }

}
