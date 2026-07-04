package com.sebitas.onixflux.integration.event;

import com.sebitas.onixflux.integration.IntegrationMetadata;
import net.minecraftforge.eventbus.api.Event;

public class IntegrationLoadedEvent extends Event {

    private final IntegrationMetadata metadata;

    public IntegrationLoadedEvent(IntegrationMetadata metadata) {
        this.metadata = metadata;
    }

    public IntegrationMetadata getMetadata() {
        return metadata;
    }

    public String getIntegrationId() {
        return metadata.id();
    }

}
