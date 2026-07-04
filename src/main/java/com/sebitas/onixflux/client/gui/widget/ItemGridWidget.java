package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.ItemGridRenderer;
import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.transmutation.FluxItemEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemGridWidget {

    private int x, y, w, h;
    private int cols, rows;
    private int scrollOffset;
    private int hoveredIndex = -1;
    private int selectedIndex = -1;
    private List<FluxItemEntry> entries = new ArrayList<>();
    private int maxScroll;
    private Consumer<Integer> onSelect;
    private Consumer<Integer> onDoubleClick;

    public ItemGridWidget(int x, int y, int w, int h, Consumer<Integer> onSelect, Consumer<Integer> onDoubleClick) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.cols = Math.max(1, (w - 4) / (ItemGridRenderer.SLOT_SIZE + ItemGridRenderer.SLOT_PAD));
        this.rows = Math.max(1, (h - 4) / (ItemGridRenderer.SLOT_SIZE + ItemGridRenderer.SLOT_PAD));
        this.onSelect = onSelect;
        this.onDoubleClick = onDoubleClick;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);
        int gx = x + 2;
        int gy = y + 2;
        ItemGridRenderer.renderGrid(gui, entries, gx, gy, cols, rows, scrollOffset, selectedIndex, hoveredIndex, partialTick);
    }

    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0 && isHovered(mx, my)) {
            int slot = ItemGridRenderer.getSlotAt((int)mx, (int)my, x + 2, y + 2, cols, scrollOffset);
            if (slot >= 0 && slot < entries.size()) {
                if (slot == selectedIndex && Screen.hasControlDown()) {
                    onDoubleClick.accept(slot);
                } else {
                    selectedIndex = slot;
                    onSelect.accept(slot);
                }
                return true;
            }
        }
        return false;
    }

    public boolean mouseScrolled(double mx, double my, double delta) {
        if (isHovered(mx, my)) {
            scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset - (int)(delta * 20)));
            return true;
        }
        return false;
    }

    public void updateEntries(List<FluxItemEntry> newEntries) {
        this.entries = new ArrayList<>(newEntries);
        int totalRows = (int) Math.ceil((double) entries.size() / cols);
        int visibleRows = rows;
        maxScroll = Math.max(0, totalRows - visibleRows) * (ItemGridRenderer.SLOT_SIZE + ItemGridRenderer.SLOT_PAD);
        scrollOffset = Math.min(scrollOffset, maxScroll);
        selectedIndex = -1;
        hoveredIndex = -1;
    }

    public void tickHover(int mx, int my) {
        if (isHovered(mx, my)) {
            hoveredIndex = ItemGridRenderer.getSlotAt(mx, my, x + 2, y + 2, cols, scrollOffset);
        } else {
            hoveredIndex = -1;
        }
    }

    public FluxItemEntry getEntry(int index) {
        if (index >= 0 && index < entries.size()) return entries.get(index);
        return null;
    }

    public int selectedIndex() {
        return selectedIndex;
    }

    public int hoveredIndex() {
        return hoveredIndex;
    }

    public boolean isHovered(double mx, double my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

}
