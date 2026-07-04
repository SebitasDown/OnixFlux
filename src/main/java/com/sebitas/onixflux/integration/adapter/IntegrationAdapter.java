package com.sebitas.onixflux.integration.adapter;

import com.sebitas.onixflux.integration.IntegrationMetadata;

public interface IntegrationAdapter {

    IntegrationMetadata metadata();

    void initialize();

    default void reload() {}

    default void shutdown() {}

    default boolean isActive() {
        return true;
    }

}
