package com.sebitas.onixflux.config;

import com.sebitas.onixflux.fx.FluxEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

public final class ConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger("ConfigLoader");

    private ConfigLoader() {}

    public static void loadAll() {
        long start = System.currentTimeMillis();
        int before = FluxEngine.size();

        Path importDir = Path.of("config", "onixflux", "import");
        if (Files.isDirectory(importDir)) {
            ConfigImporter.importDirectory(importDir);
        }

        int loaded = FluxEngine.size() - before;
        long elapsed = System.currentTimeMillis() - start;
        FluxConfigDiagnostics.recordLoad(elapsed, loaded);
        LOGGER.info("Loaded {} FX values from config ({} ms)", loaded, elapsed);
    }

}
