package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.IconRenderer;
import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class FxBarWidget {

    private int x, y, w, h;
    private long currentFx;
    private long maxFx;

    public FxBarWidget(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);

        IconRenderer.renderFxIcon(gui, x + 2, y + 2);

        int barX = x + 16;
        int barY = y + 3;
        int barW = w - 20;
        int barH = h - 6;

        gui.fill(barX, barY, barX + barW, barY + barH, 0xFF0A0A0A);
        gui.renderOutline(barX, barY, barW, barH, 0xFF1A1A2E);

        if (maxFx > 0) {
            float pct = Math.min(1.0f, (float) currentFx / maxFx);
            int fillW = (int) (pct * (barW - 2));
            if (fillW > 0) {
                gui.fill(barX + 1, barY + 1, barX + 1 + fillW, barY + barH - 1, 0xFF533483);
            }
        }

        String text = String.format("%,d / %,d", currentFx, maxFx);
        int tw = Minecraft.getInstance().font.width(text);
        gui.drawString(Minecraft.getInstance().font, text, barX + (barW - tw) / 2, barY + 2, TextRenderer.COLOR_FX, false);
    }

    public void setFx(long current, long max) {
        this.currentFx = current;
        this.maxFx = max;
    }

}
