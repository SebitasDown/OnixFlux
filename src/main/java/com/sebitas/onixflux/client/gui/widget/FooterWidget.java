package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class FooterWidget {

    private int x, y, w, h;

    public FooterWidget(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);
        String text = "OnixFlux \u00a7 1.20.1 | \u00a78Shift+Click: Stack | \u00a78Ctrl+Click: Max";
        gui.drawString(Minecraft.getInstance().font, text, x + 4, y + 4, TextRenderer.COLOR_DARK_GRAY, false);
    }

}
