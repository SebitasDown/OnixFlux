package com.sebitas.onixflux.fx;

import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public final class FluxDiagnostics {

    private int totalRecipes;
    private int totalItems;
    private int cyclesDetected;
    private int calculatedValues;
    private int manualValues;
    private long calculationTimeNanos;
    private long loadTimeNanos;

    FluxDiagnostics() {
    }

    public void recordRecipes(int count) {
        totalRecipes = count;
    }

    public void recordItems(int count) {
        totalItems = count;
    }

    public void recordCycles(int count) {
        cyclesDetected = count;
    }

    public void recordCalculated(int count) {
        calculatedValues = count;
    }

    public void recordManual(int count) {
        manualValues = count;
    }

    public void recordCalculationTime(long nanos) {
        calculationTimeNanos = nanos;
    }

    public void recordLoadTime(long nanos) {
        loadTimeNanos = nanos;
    }

    public void log(Logger logger) {
        logger.info("--- FluxEngine Diagnostics ---");
        logger.info("Total recipes loaded: {}", totalRecipes);
        logger.info("Total items registered: {}", totalItems);
        logger.info("Cycles detected: {}", cyclesDetected);
        logger.info("Calculated values: {}", calculatedValues);
        logger.info("Manual values: {}", manualValues);
        logger.info("Calculation time: {} ms", calculationTimeNanos / 1_000_000);
        logger.info("Load time: {} ms", loadTimeNanos / 1_000_000);
    }

    public Map<String, Object> snapshot() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalRecipes", totalRecipes);
        data.put("totalItems", totalItems);
        data.put("cyclesDetected", cyclesDetected);
        data.put("calculatedValues", calculatedValues);
        data.put("manualValues", manualValues);
        data.put("calculationTimeMs", calculationTimeNanos / 1_000_000);
        data.put("loadTimeMs", loadTimeNanos / 1_000_000);
        return Map.copyOf(data);
    }

}
