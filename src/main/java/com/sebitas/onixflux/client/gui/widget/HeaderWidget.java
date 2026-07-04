package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class HeaderWidget {

    private int x, y, w, h;
    private String title;

    public HeaderWidget(int x, int y, int w, int h, String title) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.title = title;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);
        gui.drawString(Minecraft.getInstance().font, title, x + 4, y + 4, TextRenderer.COLOR_ACCENT, false);
        gui.fill(x + 1, y + h - 1, x + w - 1, y + h, 0xFF533483);
    }

}
