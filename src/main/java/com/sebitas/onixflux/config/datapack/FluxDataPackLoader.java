package com.sebitas.onixflux.config.datapack;

import com.sebitas.onixflux.config.ServerConfig;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "onixflux", bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FluxDataPackLoader {

    private static final FluxDataPackReloadListener RELOAD_LISTENER = new FluxDataPackReloadListener();

    private FluxDataPackLoader() {}

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        if (ServerConfig.ENABLE_DATAPACK_VALUES.get()) {
            event.addListener(RELOAD_LISTENER);
        }
    }

    public static FluxDataPackReloadListener listener() {
        return RELOAD_LISTENER;
    }

}
