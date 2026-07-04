package com.sebitas.onixflux.config;

import com.sebitas.onixflux.OnixFlux;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigManager {

    private static final Logger LOGGER = LoggerFactory.getLogger("ConfigManager");
    private static boolean initialized = false;

    private ConfigManager() {}

    public static void initialize() {
        if (initialized) return;
        initialized = true;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        MinecraftForge.EVENT_BUS.addListener(ConfigManager::onServerStarting);

        LOGGER.info("ConfigManager initialized");
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ConfigLoader.loadAll();
    }

    private static void onServerStarting(ServerStartingEvent event) {
        if (ServerConfig.ENABLE_AUTO_RELOAD.get()) {
            ConfigWatcher.start(ConfigReloadManager::reload);
        }
    }

    public static void shutdown() {
        ConfigWatcher.stop();
    }

}
