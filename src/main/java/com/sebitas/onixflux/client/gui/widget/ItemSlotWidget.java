package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.WidgetRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ItemSlotWidget {

    private int x, y, size;
    private ItemStack stack;
    private boolean selected;
    private boolean hovered;

    public ItemSlotWidget(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.stack = ItemStack.EMPTY;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY) {
        hovered = mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
        WidgetRenderer.renderSlot(gui, x, y, size, selected, hovered);
        if (!stack.isEmpty()) {
            gui.renderItem(stack, x + 1, y + 1);
            gui.renderItemDecorations(Minecraft.getInstance().font, stack, x + 1, y + 1);
        }
    }

    public boolean mouseClicked(double mx, double my, int button) {
        return hovered;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack stack() {
        return stack;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isHovered() {
        return hovered;
    }

}
