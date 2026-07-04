package com.sebitas.onixflux.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public final class FluxConfigDiagnostics {

    private static final Logger LOGGER = LoggerFactory.getLogger("FluxConfigDiagnostics");

    private static final AtomicLong loadCount = new AtomicLong();
    private static final AtomicLong exportCount = new AtomicLong();
    private static final AtomicLong importCount = new AtomicLong();
    private static final AtomicLong reloadCount = new AtomicLong();
    private static final AtomicLong errors = new AtomicLong();
    private static final AtomicLong warnings = new AtomicLong();
    private static long totalLoadTime;
    private static long totalExportTime;
    private static int lastLoadedValues;

    private FluxConfigDiagnostics() {}

    public static void recordLoad(long timeMs, int values) {
        loadCount.incrementAndGet();
        totalLoadTime += timeMs;
        lastLoadedValues = values;
    }

    public static void recordExport(long timeMs) {
        exportCount.incrementAndGet();
        totalExportTime += timeMs;
    }

    public static void recordImport() {
        importCount.incrementAndGet();
    }

    public static void recordReload() {
        reloadCount.incrementAndGet();
    }

    public static void recordError() {
        errors.incrementAndGet();
    }

    public static void recordWarning() {
        warnings.incrementAndGet();
    }

    public static long loadCount() { return loadCount.get(); }
    public static long exportCount() { return exportCount.get(); }
    public static long importCount() { return importCount.get(); }
    public static long reloadCount() { return reloadCount.get(); }
    public static long errors() { return errors.get(); }
    public static long warnings() { return warnings.get(); }
    public static long totalLoadTime() { return totalLoadTime; }
    public static long totalExportTime() { return totalExportTime; }
    public static int lastLoadedValues() { return lastLoadedValues; }

    public static void reset() {
        loadCount.set(0);
        exportCount.set(0);
        importCount.set(0);
        reloadCount.set(0);
        errors.set(0);
        warnings.set(0);
        totalLoadTime = 0;
        totalExportTime = 0;
        lastLoadedValues = 0;
    }

    public static void log() {
        LOGGER.info("=== FluxConfig Diagnostics ===");
        LOGGER.info("Loads: {} (last: {} items, {}ms avg)", loadCount(), lastLoadedValues,
                loadCount() > 0 ? totalLoadTime / loadCount() : 0);
        LOGGER.info("Exports: {}", exportCount());
        LOGGER.info("Imports: {}", importCount());
        LOGGER.info("Reloads: {}", reloadCount());
        LOGGER.info("Errors: {} | Warnings: {}", errors(), warnings());
        LOGGER.info("===============================");
    }

}
