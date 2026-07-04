package com.sebitas.onixflux.client.gui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class TextRenderer {

    public static final int COLOR_WHITE = 0xFFE0E0E0;
    public static final int COLOR_GRAY = 0xFF888888;
    public static final int COLOR_DARK_GRAY = 0xFF555555;
    public static final int COLOR_ACCENT = 0xFF533483;
    public static final int COLOR_FX = 0xFFAA88FF;
    public static final int COLOR_SUCCESS = 0xFF88FF88;
    public static final int COLOR_ERROR = 0xFFFF8888;
    public static final int COLOR_INFO = 0xFF88AAFF;
    public static final int COLOR_GOLD = 0xFFFFAA00;

    private TextRenderer() {}

    public static void draw(GuiGraphics gui, String text, int x, int y, int color) {
        gui.drawString(Minecraft.getInstance().font, text, x, y, color, false);
    }

    public static void drawShadow(GuiGraphics gui, String text, int x, int y, int color) {
        gui.drawString(Minecraft.getInstance().font, text, x, y, color, true);
    }

    public static void draw(GuiGraphics gui, Component text, int x, int y, int color) {
        gui.drawString(Minecraft.getInstance().font, text, x, y, color, false);
    }

    public static void drawCentered(GuiGraphics gui, String text, int x, int y, int w, int color) {
        int tw = Minecraft.getInstance().font.width(text);
        gui.drawString(Minecraft.getInstance().font, text, x + (w - tw) / 2, y, color, false);
    }

    public static int width(String text) {
        return Minecraft.getInstance().font.width(text);
    }

}
