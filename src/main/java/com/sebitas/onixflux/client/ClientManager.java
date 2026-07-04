package com.sebitas.onixflux.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientManager {

    private static boolean overlayVisible = true;

    private ClientManager() {}

    public static boolean isOverlayVisible() {
        return overlayVisible;
    }

    public static void toggleOverlay() {
        overlayVisible = !overlayVisible;
    }

    public static void setOverlayVisible(boolean visible) {
        overlayVisible = visible;
    }

}
