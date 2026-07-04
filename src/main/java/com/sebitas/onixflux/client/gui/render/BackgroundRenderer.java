package com.sebitas.onixflux.client.gui.render;

import net.minecraft.client.gui.GuiGraphics;

public final class BackgroundRenderer {

    private static final int COLOR_BG = 0xC00A0A0A;
    private static final int COLOR_BORDER = 0xFF1A1A2E;
    private static final int COLOR_INNER_BORDER = 0xFF16213E;

    private BackgroundRenderer() {}

    public static void renderPanel(GuiGraphics gui, int x, int y, int w, int h) {
        gui.fill(x, y, x + w, y + h, COLOR_BG);
        gui.renderOutline(x, y, w, h, COLOR_BORDER);
        gui.fill(x + 1, y + 1, x + w - 1, y + h - 1, COLOR_INNER_BORDER);
    }

    public static void renderPanel(GuiGraphics gui, int x, int y, int w, int h, int bg, int border) {
        gui.fill(x, y, x + w, y + h, bg);
        gui.renderOutline(x, y, w, h, border);
    }

    public static void renderFull(GuiGraphics gui, int screenW, int screenH) {
        gui.fill(0, 0, screenW, screenH, 0xC0000000);
    }

}
