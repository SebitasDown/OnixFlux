package com.sebitas.onixflux.integration;

import com.sebitas.onixflux.integration.adapter.IntegrationAdapter;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class IntegrationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger("IntegrationLoader");
    private static final List<String> disabledIntegrations = new CopyOnWriteArrayList<>();
    private static final List<String> blacklist = new CopyOnWriteArrayList<>();
    private static final List<String> whitelist = new CopyOnWriteArrayList<>();
    private static boolean whitelistMode = false;

    private IntegrationLoader() {}

    public static void load(IntegrationAdapter adapter) {
        var start = System.currentTimeMillis();
        var metadata = adapter.metadata();
        var id = metadata.id();

        try {
            if (disabledIntegrations.contains(id)) {
                IntegrationDiagnostics.recordSkipped(id, "Integration is disabled");
                return;
            }

            if (blacklist.contains(id)) {
                IntegrationDiagnostics.recordSkipped(id, "Integration is blacklisted");
                return;
            }

            if (whitelistMode && !whitelist.contains(id)) {
                IntegrationDiagnostics.recordSkipped(id, "Integration is not in whitelist");
                return;
            }

            var validation = IntegrationValidator.validate(metadata);
            if (!validation.isValid()) {
                IntegrationDiagnostics.recordError(id, "Validation failed: " + String.join(", ", validation.errors()));
                return;
            }
            if (!validation.warnings().isEmpty()) {
                LOGGER.warn("Integration {} warnings: {}", id, validation.warnings());
            }

            if (!ModList.get().isLoaded(metadata.modId())) {
                IntegrationDiagnostics.recordSkipped(id, "Mod " + metadata.modId() + " not installed");
                return;
            }

            IntegrationDiagnostics.recordDetectedMod(metadata.modId());
            adapter.initialize();
            IntegrationRegistry.register(adapter);
            IntegrationDiagnostics.recordLoad(id, System.currentTimeMillis() - start);
            LOGGER.info("Integration {} loaded successfully", id);
        } catch (Exception e) {
            IntegrationDiagnostics.recordError(id, e.getMessage());
            LOGGER.error("Failed to load integration {}: {}", id, e.getMessage());
        }
    }

    public static void loadAll(List<IntegrationAdapter> adapters) {
        for (var adapter : adapters) {
            load(adapter);
        }
    }

    public static void disable(String id) {
        if (!disabledIntegrations.contains(id)) {
            disabledIntegrations.add(id);
        }
    }

    public static void enable(String id) {
        disabledIntegrations.remove(id);
    }

    public static void blacklist(String id) {
        if (!blacklist.contains(id)) {
            blacklist.add(id);
        }
    }

    public static void whitelist(String id) {
        if (!whitelist.contains(id)) {
            whitelist.add(id);
        }
    }

    public static void setWhitelistMode(boolean mode) {
        whitelistMode = mode;
    }

    public static boolean isWhitelistMode() {
        return whitelistMode;
    }

    public static List<String> disabled() {
        return Collections.unmodifiableList(disabledIntegrations);
    }

    public static List<String> blacklist() {
        return Collections.unmodifiableList(blacklist);
    }

    public static List<String> whitelist() {
        return Collections.unmodifiableList(whitelist);
    }

}
