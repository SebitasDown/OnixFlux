package com.sebitas.onixflux.config;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigExporter {

    private static final Logger LOGGER = LoggerFactory.getLogger("ConfigExporter");

    private ConfigExporter() {}

    public static void exportJson(Map<ResourceLocation, Long> values) {
        export(values, "json");
    }

    public static void exportCsv(Map<ResourceLocation, Long> values) {
        export(values, "csv");
    }

    public static void exportToml(Map<ResourceLocation, Long> values) {
        export(values, "toml");
    }

    public static void exportAll(Map<ResourceLocation, Long> values) {
        exportJson(values);
        exportCsv(values);
        exportToml(values);
    }

    private static void export(Map<ResourceLocation, Long> values, String format) {
        long start = System.currentTimeMillis();
        Path dir = Path.of("config", OnixFlux.MOD_ID, "export");
        try {
            Files.createDirectories(dir);
            Path file = dir.resolve("fx_values." + format);
            String content;
            switch (format) {
                case "json" -> content = ConfigSerializer.toJson(values);
                case "csv" -> content = ConfigSerializer.toCsv(values);
                case "toml" -> content = ConfigSerializer.toToml(values);
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            }
            Files.writeString(file, content, StandardCharsets.UTF_8);
            long elapsed = System.currentTimeMillis() - start;
            FluxConfigDiagnostics.recordExport(elapsed);
            LOGGER.info("Exported {} values to {} ({} ms)", values.size(), file.getFileName(), elapsed);
        } catch (IOException e) {
            FluxConfigDiagnostics.recordError();
            LOGGER.error("Failed to export to {}: {}", format, e.getMessage());
        }
    }

}
