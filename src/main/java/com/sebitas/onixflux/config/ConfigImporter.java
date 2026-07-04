package com.sebitas.onixflux.config;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Map;

public final class ConfigImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger("ConfigImporter");

    private ConfigImporter() {}

    public static void importJson(Path file) {
        importFile(file, "json");
    }

    public static void importCsv(Path file) {
        importFile(file, "csv");
    }

    public static void importToml(Path file) {
        importFile(file, "toml");
    }

    public static void importDirectory(Path dir) {
        if (!Files.isDirectory(dir)) {
            LOGGER.warn("Not a directory: {}", dir);
            return;
        }
        try (var files = Files.walk(dir)) {
            files.filter(Files::isRegularFile).forEach(ConfigImporter::importSingleFile);
        } catch (IOException e) {
            FluxConfigDiagnostics.recordError();
            LOGGER.error("Failed to scan import directory: {}", e.getMessage());
        }
    }

    private static void importSingleFile(Path file) {
        String name = file.getFileName().toString().toLowerCase();
        if (name.endsWith(".json")) importJson(file);
        else if (name.endsWith(".csv")) importCsv(file);
        else if (name.endsWith(".toml")) importToml(file);
    }

    private static void importFile(Path file, String format) {
        try {
            String content = Files.readString(file, StandardCharsets.UTF_8);
            Map<ResourceLocation, Long> values;
            switch (format) {
                case "json" -> values = ConfigSerializer.fromJson(content);
                case "csv" -> values = ConfigSerializer.fromCsv(content);
                case "toml" -> values = ConfigSerializer.fromToml(content);
                default -> throw new IllegalArgumentException("Unsupported format: " + format);
            }
            int count = 0;
            for (Map.Entry<ResourceLocation, Long> entry : values.entrySet()) {
                net.minecraft.world.item.Item item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(entry.getKey());
                if (item != null && item != net.minecraft.world.item.Items.AIR) {
                    FluxEngine.register(item, entry.getValue(), FluxSource.CONFIG);
                    count++;
                }
            }
            FluxConfigDiagnostics.recordImport();
            LOGGER.info("Imported {} values from {} ({} registered)", values.size(), file.getFileName(), count);
        } catch (IOException e) {
            FluxConfigDiagnostics.recordError();
            LOGGER.error("Failed to import {}: {}", format, e.getMessage());
        }
    }

}
