package com.sebitas.onixflux.integration;

import java.util.Set;

public record IntegrationMetadata(
        String id,
        String name,
        String modId,
        String version,
        Set<String> dependencies,
        IntegrationPriority priority,
        boolean clientSide,
        boolean serverSide
) {

    public boolean requiresClient() {
        return clientSide;
    }

    public boolean requiresServer() {
        return serverSide;
    }

    public boolean hasDependencies() {
        return dependencies != null && !dependencies.isEmpty();
    }

}
