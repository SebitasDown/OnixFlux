package com.sebitas.onixflux.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class IntegrationDiagnostics {

    private static final Logger LOGGER = LoggerFactory.getLogger("IntegrationDiagnostics");
    private static final Map<String, IntegrationRecord> records = new ConcurrentHashMap<>();
    private static final List<String> errorLog = new CopyOnWriteArrayList<>();
    private static final List<String> detectedMods = new CopyOnWriteArrayList<>();

    private IntegrationDiagnostics() {}

    public static void recordLoad(String integrationId, long elapsedMs) {
        records.put(integrationId, new IntegrationRecord(integrationId, Status.LOADED, elapsedMs, null));
        LOGGER.debug("Integration {} loaded in {} ms", integrationId, elapsedMs);
    }

    public static void recordError(String integrationId, String error) {
        records.put(integrationId, new IntegrationRecord(integrationId, Status.ERROR, 0, error));
        errorLog.add("[" + integrationId + "] " + error);
        LOGGER.error("Integration {} error: {}", integrationId, error);
    }

    public static void recordSkipped(String integrationId, String reason) {
        records.put(integrationId, new IntegrationRecord(integrationId, Status.SKIPPED, 0, reason));
        LOGGER.debug("Integration {} skipped: {}", integrationId, reason);
    }

    public static void recordLoaded(String integrationId) {
        recordLoad(integrationId, 0);
    }

    public static void recordDetectedMod(String modId) {
        if (!detectedMods.contains(modId)) {
            detectedMods.add(modId);
            LOGGER.info("Detected mod: {}", modId);
        }
    }

    public static IntegrationRecord getRecord(String integrationId) {
        return records.get(integrationId);
    }

    public static Map<String, IntegrationRecord> allRecords() {
        return Collections.unmodifiableMap(records);
    }

    public static List<String> errorLog() {
        return Collections.unmodifiableList(errorLog);
    }

    public static List<String> detectedMods() {
        return Collections.unmodifiableList(detectedMods);
    }

    public static boolean hasErrors() {
        return !errorLog.isEmpty();
    }

    public static void clear() {
        records.clear();
        errorLog.clear();
        detectedMods.clear();
    }

    public enum Status {
        LOADED,
        ERROR,
        SKIPPED
    }

    public record IntegrationRecord(String integrationId, Status status, long elapsedMs, String detail) {}

    public record DiagnosticsReport(
            int totalIntegrations,
            int activeIntegrations,
            Map<String, IntegrationRecord> records,
            List<String> detectedMods,
            List<String> errors
    ) {}

}
