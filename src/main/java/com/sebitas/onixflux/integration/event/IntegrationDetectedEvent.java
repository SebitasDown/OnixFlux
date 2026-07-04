package com.sebitas.onixflux.integration.event;

import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class IntegrationDetectedEvent extends Event {

    private final List<String> detectedMods;

    public IntegrationDetectedEvent(List<String> detectedMods) {
        this.detectedMods = detectedMods;
    }

    public List<String> getDetectedMods() {
        return detectedMods;
    }

    public boolean isModPresent(String modId) {
        return detectedMods.contains(modId);
    }

}
