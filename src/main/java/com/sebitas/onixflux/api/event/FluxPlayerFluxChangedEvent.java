package com.sebitas.onixflux.api.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class FluxPlayerFluxChangedEvent extends Event {

    private final Player player;
    private final long oldValue;
    private final long newValue;

    public FluxPlayerFluxChangedEvent(Player player, long oldValue, long newValue) {
        this.player = player;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Player getPlayer() {
        return player;
    }

    public long getOldValue() {
        return oldValue;
    }

    public long getNewValue() {
        return newValue;
    }

    public long getDifference() {
        return newValue - oldValue;
    }

}
