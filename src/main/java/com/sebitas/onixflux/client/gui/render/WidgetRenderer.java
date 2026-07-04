package com.sebitas.onixflux.client.gui.render;

import com.sebitas.onixflux.client.gui.animation.Easing;
import net.minecraft.client.gui.GuiGraphics;

public final class WidgetRenderer {

    private WidgetRenderer() {}

    public static void renderSlot(GuiGraphics gui, int x, int y, int size, boolean selected, boolean hovered) {
        int bg = selected ? 0x550F3460 : (hovered ? 0x33533483 : 0x221A1A2E);
        gui.fill(x, y, x + size, y + size, bg);
        if (selected) gui.renderOutline(x, y, size, size, 0xFF533483);
        else if (hovered) gui.renderOutline(x, y, size, size, 0xFF0F3460);
    }

    public static void renderButton(GuiGraphics gui, int x, int y, int w, int h, boolean hovered, boolean active) {
        int bg = active ? 0xFF0F3460 : (hovered ? 0xFF1A1A2E : 0xFF16213E);
        gui.fill(x, y, x + w, y + h, bg);
        gui.renderOutline(x, y, w, h, active ? 0xFF533483 : 0xFF1A1A2E);
    }

    public static void renderScrollingText(GuiGraphics gui, String text, int x, int y, int maxW, int color, net.minecraft.client.gui.Font font) {
        int tw = font.width(text);
        if (tw <= maxW) {
            gui.drawString(font, text, x, y, color, false);
        } else {
            gui.enableScissor(x, y, x + maxW, y + font.lineHeight);
            gui.drawString(font, text, x, y, color, false);
            gui.disableScissor();
        }
    }

}
