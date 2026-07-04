package com.sebitas.onixflux.client.gui;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.transmutation.FluxTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OnixFlux.MOD_ID, value = Dist.CLIENT)
public final class FluxScreenManager {

    private static FluxTableScreen activeScreen;

    private FluxScreenManager() {}

    public static void open(FluxTableMenu menu) {
        var mc = Minecraft.getInstance();
        var screen = new FluxTableScreen(menu, mc.player.getInventory(), Component.translatable("container.onixflux.flux_table"));
        mc.setScreen(screen);
        activeScreen = screen;
    }

    public static void close() {
        var mc = Minecraft.getInstance();
        if (mc.player != null) mc.player.closeContainer();
        activeScreen = null;
    }

    public static FluxTableScreen getActiveScreen() {
        return activeScreen;
    }

    @SubscribeEvent
    public static void onScreenClose(ScreenEvent.Closing event) {
        if (event.getScreen() instanceof FluxTableScreen) {
            activeScreen = null;
        }
    }

}
