package com.sebitas.onixflux.client.gui.render;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public final class IconRenderer {

    private static final ResourceLocation ICONS = ResourceLocation.fromNamespaceAndPath(OnixFlux.MOD_ID, "textures/gui/icons.png");

    private IconRenderer() {}

    public static void renderIcon(GuiGraphics gui, int x, int y, int u, int v, int size) {
        gui.blit(ICONS, x, y, u, v, size, size, 64, 64);
    }

    public static void renderSearchIcon(GuiGraphics gui, int x, int y) {
        renderIcon(gui, x, y, 0, 0, 12);
    }

    public static void renderFavoriteIcon(GuiGraphics gui, int x, int y, boolean filled) {
        renderIcon(gui, x, y, filled ? 12 : 24, 0, 12);
    }

    public static void renderHistoryIcon(GuiGraphics gui, int x, int y) {
        renderIcon(gui, x, y, 36, 0, 12);
    }

    public static void renderLockIcon(GuiGraphics gui, int x, int y, boolean locked) {
        renderIcon(gui, x, y, locked ? 0 : 12, 12, 12);
    }

    public static void renderFxIcon(GuiGraphics gui, int x, int y) {
        renderIcon(gui, x, y, 24, 12, 12);
    }

    public static void renderFilterIcon(GuiGraphics gui, int x, int y) {
        renderIcon(gui, x, y, 36, 12, 12);
    }

    public static void renderCategoryIcon(GuiGraphics gui, int x, int y) {
        renderIcon(gui, x, y, 48, 0, 12);
    }

    public static void renderLearnIcon(GuiGraphics gui, int x, int y) {
        renderIcon(gui, x, y, 48, 12, 12);
    }

    public static void renderScrollIcon(GuiGraphics gui, int x, int y) {
        renderIcon(gui, x, y, 0, 24, 8);
    }

}
