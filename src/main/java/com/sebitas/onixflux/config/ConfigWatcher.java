package com.sebitas.onixflux.config;

import com.sebitas.onixflux.OnixFlux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ConfigWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger("ConfigWatcher");
    private static ScheduledExecutorService executor;
    private static Map<Path, Long> fileTimestamps = new HashMap<>();
    private static Runnable reloadCallback;

    private ConfigWatcher() {}

    public static void start(Runnable onReload) {
        reloadCallback = onReload;
        Path configDir = Path.of("config", OnixFlux.MOD_ID);
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            LOGGER.warn("Could not create config directory: {}", e.getMessage());
        }
        scanFiles(configDir);
        if (ServerConfig.AUTO_RELOAD_INTERVAL.get() > 0) {
            executor = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "onixflux-config-watcher");
                t.setDaemon(true);
                return t;
            });
            executor.scheduleAtFixedRate(ConfigWatcher::check, 0,
                    ServerConfig.AUTO_RELOAD_INTERVAL.get(), TimeUnit.SECONDS);
        }
    }

    public static void stop() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }

    private static void scanFiles(Path dir) {
        try (var files = Files.walk(dir)) {
            files.filter(Files::isRegularFile)
                 .filter(p -> p.toString().endsWith(".json") || p.toString().endsWith(".toml") || p.toString().endsWith(".csv"))
                 .forEach(p -> fileTimestamps.put(p, p.toFile().lastModified()));
        } catch (IOException e) {
            LOGGER.warn("Error scanning config files: {}", e.getMessage());
        }
    }

    private static void check() {
        boolean changed = false;
        Path configDir = Path.of("config", OnixFlux.MOD_ID);
        try {
            if (!Files.exists(configDir)) return;
            try (var files = Files.walk(configDir)) {
                for (var path : (Iterable<Path>) files::iterator) {
                    if (!Files.isRegularFile(path)) continue;
                    long lastMod = path.toFile().lastModified();
                    Long prev = fileTimestamps.get(path);
                    if (prev == null || prev != lastMod) {
                        fileTimestamps.put(path, lastMod);
                        changed = true;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.warn("Error watching config files: {}", e.getMessage());
        }
        if (changed && reloadCallback != null) {
            LOGGER.info("Config file change detected, reloading...");
            reloadCallback.run();
        }
    }

}
