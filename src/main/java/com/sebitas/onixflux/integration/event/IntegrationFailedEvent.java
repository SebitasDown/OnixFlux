package com.sebitas.onixflux.integration.event;

import com.sebitas.onixflux.integration.IntegrationMetadata;
import net.minecraftforge.eventbus.api.Event;

public class IntegrationFailedEvent extends Event {

    private final IntegrationMetadata metadata;
    private final String reason;

    public IntegrationFailedEvent(IntegrationMetadata metadata, String reason) {
        this.metadata = metadata;
        this.reason = reason;
    }

    public IntegrationMetadata getMetadata() {
        return metadata;
    }

    public String getReason() {
        return reason;
    }

    public String getIntegrationId() {
        return metadata.id();
    }

}
