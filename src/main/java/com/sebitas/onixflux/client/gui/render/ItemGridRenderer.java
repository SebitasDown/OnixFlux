package com.sebitas.onixflux.client.gui.render;

import com.sebitas.onixflux.transmutation.FluxItemEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class ItemGridRenderer {

    public static final int SLOT_SIZE = 18;
    public static final int SLOT_PAD = 1;

    private ItemGridRenderer() {}

    public static void renderGrid(GuiGraphics gui, List<FluxItemEntry> entries, int startX, int startY,
                                   int cols, int rows, int scrollOffset, int selectedIndex,
                                   int hoveredIndex, float partialTick) {
        for (int i = 0; i < entries.size(); i++) {
            int col = i % cols;
            int row = i / cols;
            int x = startX + col * (SLOT_SIZE + SLOT_PAD);
            int y = startY + row * (SLOT_SIZE + SLOT_PAD) - scrollOffset;

            int slotBottom = y + SLOT_SIZE;
            int gridBottom = startY + rows * (SLOT_SIZE + SLOT_PAD);
            if (slotBottom < startY || y > gridBottom) continue;

            boolean selected = i == selectedIndex;
            boolean hovered = i == hoveredIndex;

            WidgetRenderer.renderSlot(gui, x, y, SLOT_SIZE, selected, hovered);

            var mc = Minecraft.getInstance();
            gui.renderItem(new ItemStack(entries.get(i).item()), x + 1, y + 1);
            gui.renderItemDecorations(mc.font, new ItemStack(entries.get(i).item()), x + 1, y + 1);
        }
    }

    public static int getSlotAt(int mouseX, int mouseY, int startX, int startY, int cols, int scrollOffset) {
        int col = (mouseX - startX) / (SLOT_SIZE + SLOT_PAD);
        int row = (mouseY - startY + scrollOffset) / (SLOT_SIZE + SLOT_PAD);
        if (col < 0 || col >= cols || row < 0) return -1;
        return row * cols + col;
    }

}
