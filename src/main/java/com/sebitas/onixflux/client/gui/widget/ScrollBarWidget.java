package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import net.minecraft.client.gui.GuiGraphics;

public class ScrollBarWidget {

    private int x, y, w, h;
    private int maxScroll;
    private int scrollOffset;
    private boolean dragging;

    public ScrollBarWidget(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);

        if (maxScroll <= 0) return;

        float thumbHeight = Math.max(12, (float) h * h / (h + maxScroll));
        float thumbPos = (float) scrollOffset / maxScroll * (h - thumbHeight);

        int tx = x + 2;
        int ty = (int) (y + 2 + thumbPos);
        int tw = w - 4;
        int th = (int) thumbHeight;

        int thumbColor = (isHoveredThumb(mouseX, mouseY) || dragging) ? 0xFF533483 : 0xFF0F3460;
        gui.fill(tx, ty, tx + tw, ty + th, thumbColor);
    }

    public boolean mouseClicked(double mx, double my, int button) {
        if (isHoveredThumb((int) mx, (int) my)) {
            dragging = true;
            return true;
        }
        return false;
    }

    public void mouseReleased() {
        dragging = false;
    }

    public boolean isHoveredThumb(int mx, int my) {
        if (maxScroll <= 0) return false;
        float thumbHeight = Math.max(12, (float) h * h / (h + maxScroll));
        float thumbPos = (float) scrollOffset / maxScroll * (h - thumbHeight);
        int tx = x + 2;
        int ty = (int) (y + 2 + thumbPos);
        return mx >= tx && mx <= tx + w - 4 && my >= ty && my <= ty + (int) thumbHeight;
    }

    public boolean isHovered(double mx, double my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void setMaxScroll(int maxScroll) { this.maxScroll = maxScroll; }
    public void setScrollOffset(int scrollOffset) { this.scrollOffset = scrollOffset; }

}
