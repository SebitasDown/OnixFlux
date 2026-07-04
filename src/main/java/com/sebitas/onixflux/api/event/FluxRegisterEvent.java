package com.sebitas.onixflux.api.event;

import com.sebitas.onixflux.fx.FluxValue;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class FluxRegisterEvent extends Event {

    private final Item item;
    private final FluxValue value;
    private final boolean replaced;

    public FluxRegisterEvent(Item item, FluxValue value, boolean replaced) {
        this.item = item;
        this.value = value;
        this.replaced = replaced;
    }

    public Item getItem() {
        return item;
    }

    public FluxValue getValue() {
        return value;
    }

    public boolean isReplaced() {
        return replaced;
    }

}
