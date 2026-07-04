package com.sebitas.onixflux.integration;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.integration.adapter.IntegrationAdapter;
import com.sebitas.onixflux.integration.event.*;
import com.sebitas.onixflux.integration.plugins.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class IntegrationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger("IntegrationManager");
    private static final List<IntegrationAdapter> pending = new CopyOnWriteArrayList<>();
    private static boolean initialized = false;
    private static boolean loaded = false;

    private IntegrationManager() {}

    public static void initialize() {
        if (initialized) return;
        initialized = true;

        LOGGER.info("Initializing IntegrationManager");

        registerBuiltinAdapters();

        MinecraftForge.EVENT_BUS.post(new IntegrationDetectedEvent(detectedMods()));
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        if (loaded) return;
        loaded = true;

        event.enqueueWork(() -> {
            LOGGER.info("Loading integrations...");
            IntegrationLoader.loadAll(pending);
            pending.clear();

            var active = IntegrationRegistry.active();
            LOGGER.info("Loaded {} integrations", active.size());

            for (var adapter : active) {
                MinecraftForge.EVENT_BUS.post(new IntegrationLoadedEvent(adapter.metadata()));
            }
        });
    }

    public static void reload() {
        LOGGER.info("Reloading all integrations...");
        for (var adapter : IntegrationRegistry.active()) {
            try {
                adapter.reload();
                MinecraftForge.EVENT_BUS.post(new IntegrationReloadEvent(adapter.metadata()));
            } catch (Exception e) {
                IntegrationDiagnostics.recordError(adapter.metadata().id(), "Reload failed: " + e.getMessage());
                MinecraftForge.EVENT_BUS.post(new IntegrationFailedEvent(adapter.metadata(), e.getMessage()));
            }
        }
    }

    public static void shutdown() {
        LOGGER.info("Shutting down integrations...");
        for (var adapter : IntegrationRegistry.active()) {
            try {
                adapter.shutdown();
            } catch (Exception e) {
                LOGGER.error("Error shutting down integration {}: {}", adapter.metadata().id(), e.getMessage());
            }
        }
        IntegrationRegistry.clear();
        IntegrationDiagnostics.clear();
        pending.clear();
        initialized = false;
        loaded = false;
    }

    public static boolean isIntegrationLoaded(String id) {
        return IntegrationRegistry.isRegistered(id);
    }

    public static boolean isModIntegrated(String modId) {
        return IntegrationRegistry.all().stream()
                .anyMatch(a -> a.metadata().modId().equals(modId));
    }

    public static List<String> detectedMods() {
        return ModList.get().getMods().stream()
                .map(m -> m.getModId())
                .collect(Collectors.toList());
    }

    public static IntegrationDiagnostics.DiagnosticsReport generateReport() {
        return new IntegrationDiagnostics.DiagnosticsReport(
                IntegrationRegistry.size(),
                IntegrationRegistry.active().size(),
                IntegrationDiagnostics.allRecords(),
                IntegrationDiagnostics.detectedMods(),
                IntegrationDiagnostics.errorLog()
        );
    }

    private static void registerBuiltinAdapters() {
        register(new JEIIntegration());
        register(new EMIIntegration());
        register(new JadeIntegration());
        register(new TheOneProbeIntegration());
        register(new CuriosIntegration());
        register(new CreateIntegration());
        register(new BotaniaIntegration());
        register(new MekanismIntegration());
        register(new ThermalSeriesIntegration());
        register(new IndustrialForegoingIntegration());
        register(new AE2Integration());
        register(new RefinedStorageIntegration());
        register(new KubeJSIntegration());
        register(new CraftTweakerIntegration());
        register(new SophisticatedBackpacksIntegration());
        register(new TinkersConstructIntegration());
        register(new FarmersDelightIntegration());
        register(new QuarkIntegration());
    }

    public static void register(IntegrationAdapter adapter) {
        if (adapter == null) return;
        pending.add(adapter);
    }

}
