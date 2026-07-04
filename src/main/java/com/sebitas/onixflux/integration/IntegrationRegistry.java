package com.sebitas.onixflux.integration;

import com.sebitas.onixflux.integration.adapter.IntegrationAdapter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class IntegrationRegistry {

    private static final Map<String, IntegrationAdapter> adapters = new LinkedHashMap<>();
    private static final Map<String, IntegrationMetadata> metadataMap = new ConcurrentHashMap<>();

    private IntegrationRegistry() {}

    public static void register(IntegrationAdapter adapter) {
        var metadata = adapter.metadata();
        if (adapters.containsKey(metadata.id())) {
            IntegrationDiagnostics.recordError(metadata.id(), "Duplicate registration for " + metadata.id());
            return;
        }
        adapters.put(metadata.id(), adapter);
        metadataMap.put(metadata.id(), metadata);
        IntegrationDiagnostics.recordLoaded(metadata.id());
    }

    public static void unregister(String id) {
        adapters.remove(id);
        metadataMap.remove(id);
    }

    public static Optional<IntegrationAdapter> get(String id) {
        return Optional.ofNullable(adapters.get(id));
    }

    public static Collection<IntegrationAdapter> all() {
        return Collections.unmodifiableCollection(adapters.values());
    }

    public static List<IntegrationAdapter> active() {
        return adapters.values().stream()
                .filter(IntegrationAdapter::isActive)
                .collect(Collectors.toList());
    }

    public static List<IntegrationAdapter> sortedByPriority() {
        return adapters.values().stream()
                .sorted((a, b) -> Integer.compare(
                        b.metadata().priority().value(),
                        a.metadata().priority().value()))
                .collect(Collectors.toList());
    }

    public static boolean isRegistered(String id) {
        return adapters.containsKey(id);
    }

    public static int size() {
        return adapters.size();
    }

    public static void clear() {
        adapters.clear();
        metadataMap.clear();
    }

    public static Optional<IntegrationMetadata> metadata(String id) {
        return Optional.ofNullable(metadataMap.get(id));
    }

    public static Collection<IntegrationMetadata> allMetadata() {
        return Collections.unmodifiableCollection(metadataMap.values());
    }

}
