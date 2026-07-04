package com.sebitas.onixflux.api.event;

import net.minecraftforge.eventbus.api.Event;

public class FluxReloadEvent extends Event {

    private final boolean success;

    public FluxReloadEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}
