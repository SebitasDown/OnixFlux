package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.IconRenderer;
import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class FavoritesWidget {

    private int x, y, w, h;
    private List<Integer> favoriteSlots;

    public FavoritesWidget(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.favoriteSlots = List.of();
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);
        IconRenderer.renderFavoriteIcon(gui, x + 2, y + 2, true);
        gui.drawString(net.minecraft.client.Minecraft.getInstance().font, "Favorites", x + 16, y + 3, 0xFF888888, false);
        if (favoriteSlots.isEmpty()) {
            gui.drawString(net.minecraft.client.Minecraft.getInstance().font, "No favorites yet", x + 4, y + 18, 0xFF555555, false);
        }
    }

    public void setFavorites(List<Integer> slots) {
        this.favoriteSlots = slots;
    }

    public boolean isHovered(double mx, double my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

}
