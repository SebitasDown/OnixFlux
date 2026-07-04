package com.sebitas.onixflux.client.gui.render;

import net.minecraft.client.gui.GuiGraphics;

public final class PanelRenderer {

    public static final int PANEL_BG = 0xC00A0A0A;
    public static final int PANEL_BORDER = 0xFF1A1A2E;
    public static final int PANEL_INNER = 0xFF16213E;
    public static final int PANEL_ACCENT = 0xFF0F3460;
    public static final int PANEL_HIGHLIGHT = 0xFF533483;

    private PanelRenderer() {}

    public static void render(GuiGraphics gui, int x, int y, int w, int h) {
        gui.fill(x, y, x + w, y + h, PANEL_BG);
        gui.renderOutline(x, y, w, h, PANEL_BORDER);
        gui.fill(x + 1, y + 1, x + w - 1, y + 1 + 1, PANEL_ACCENT);
    }

    public static void renderAccented(GuiGraphics gui, int x, int y, int w, int h) {
        gui.fill(x, y, x + w, y + h, PANEL_BG);
        gui.renderOutline(x, y, w, h, PANEL_HIGHLIGHT);
        gui.fill(x + 1, y + 1, x + w - 1, y + h - 1, 0x220F3460);
    }

    public static void renderHighlight(GuiGraphics gui, int x, int y, int w, int h, float alpha) {
        int a = (int) (alpha * 255);
        gui.fill(x, y, x + w, y + h, (a << 24) | 0x533483);
    }

}
