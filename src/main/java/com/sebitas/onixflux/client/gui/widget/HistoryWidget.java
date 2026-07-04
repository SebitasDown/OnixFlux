package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.IconRenderer;
import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class HistoryWidget {

    private int x, y, w, h;
    private List<String> recentItems;

    public HistoryWidget(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.recentItems = List.of();
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);
        IconRenderer.renderHistoryIcon(gui, x + 2, y + 2);
        gui.drawString(net.minecraft.client.Minecraft.getInstance().font, "History", x + 16, y + 3, 0xFF888888, false);
        if (recentItems.isEmpty()) {
            gui.drawString(net.minecraft.client.Minecraft.getInstance().font, "No history yet", x + 4, y + 18, 0xFF555555, false);
        } else {
            int ly = y + 18;
            for (String item : recentItems) {
                gui.drawString(net.minecraft.client.Minecraft.getInstance().font, item, x + 4, ly, 0xFF555555, false);
                ly += 10;
            }
        }
    }

    public void setRecentItems(List<String> items) {
        this.recentItems = items;
    }

    public boolean isHovered(double mx, double my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

}
