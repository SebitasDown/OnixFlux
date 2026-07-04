package com.sebitas.onixflux.config;

import com.sebitas.onixflux.api.FluxAPI;
import com.sebitas.onixflux.api.event.FluxReloadEvent;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigReloadManager {

    private static final Logger LOGGER = LoggerFactory.getLogger("ConfigReloadManager");

    private ConfigReloadManager() {}

    public static void reload() {
        long start = System.currentTimeMillis();
        try {
            ConfigLoader.loadAll();
            long elapsed = System.currentTimeMillis() - start;
            LOGGER.info("Config reloaded in {} ms", elapsed);
            MinecraftForge.EVENT_BUS.post(new FluxReloadEvent(true));
        } catch (Exception e) {
            FluxConfigDiagnostics.recordError();
            LOGGER.error("Config reload failed: {}", e.getMessage());
            MinecraftForge.EVENT_BUS.post(new FluxReloadEvent(false));
        }
    }

}
