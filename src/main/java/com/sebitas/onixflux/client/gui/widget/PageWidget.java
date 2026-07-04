package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class PageWidget {

    private int x, y, w, h;
    private int currentPage;
    private int totalPages;
    private Runnable onPrev;
    private Runnable onNext;

    public PageWidget(int x, int y, int w, int h, Runnable onPrev, Runnable onNext) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.onPrev = onPrev;
        this.onNext = onNext;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);

        String text = "Page " + (currentPage + 1) + " / " + totalPages;
        int tw = Minecraft.getInstance().font.width(text);
        gui.drawString(Minecraft.getInstance().font, text, x + (w - tw) / 2, y + 4, TextRenderer.COLOR_GRAY, false);

        if (totalPages > 0) {
            String prev = "<";
            String next = ">";
            gui.drawString(Minecraft.getInstance().font, prev, x + 4, y + 4,
                    hasPrev() && isHoveredPrev(mouseX, mouseY) ? TextRenderer.COLOR_ACCENT : TextRenderer.COLOR_DARK_GRAY, false);
            gui.drawString(Minecraft.getInstance().font, next, x + w - 10, y + 4,
                    hasNext() && isHoveredNext(mouseX, mouseY) ? TextRenderer.COLOR_ACCENT : TextRenderer.COLOR_DARK_GRAY, false);
        }
    }

    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0 && totalPages > 0) {
            if (hasPrev() && isHoveredPrev((int)mx, (int)my)) {
                onPrev.run();
                return true;
            }
            if (hasNext() && isHoveredNext((int)mx, (int)my)) {
                onNext.run();
                return true;
            }
        }
        return false;
    }

    private boolean isHoveredPrev(int mx, int my) {
        return mx >= x && mx <= x + 12 && my >= y && my <= y + h;
    }

    private boolean isHoveredNext(int mx, int my) {
        return mx >= x + w - 12 && mx <= x + w && my >= y && my <= y + h;
    }

    public boolean hasPrev() { return currentPage > 0; }
    public boolean hasNext() { return currentPage < totalPages - 1; }

    public void setPage(int current, int total) {
        this.currentPage = current;
        this.totalPages = total;
    }

}
