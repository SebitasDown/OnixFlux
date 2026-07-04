package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class LearnSlotWidget {

    private int x, y, size;
    private boolean hasStack;
    private boolean inProgress;

    public LearnSlotWidget(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        boolean hovered = mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
        int bg = inProgress ? 0x33533483 : (hasStack ? 0x220F3460 : 0x221A1A2E);
        gui.fill(x, y, x + size, y + size, bg);
        gui.renderOutline(x, y, size, size, inProgress ? 0xFF533483 : (hasStack ? 0xFF0F3460 : 0xFF1A1A2E));

        if (inProgress) {
            String text = "Learning...";
            int tw = Minecraft.getInstance().font.width(text);
            gui.drawString(Minecraft.getInstance().font, text, x + (size - tw) / 2, y + size / 2 - 4, TextRenderer.COLOR_FX, false);
        } else if (!hasStack) {
            String text = "Place item";
            int tw = Minecraft.getInstance().font.width(text);
            gui.drawString(Minecraft.getInstance().font, text, x + (size - tw) / 2, y + size / 2 - 4, TextRenderer.COLOR_DARK_GRAY, false);
        }
    }

    public void setHasStack(boolean hasStack) {
        this.hasStack = hasStack;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public boolean isHovered(double mx, double my) {
        return mx >= x && mx <= x + size && my >= y && my <= y + size;
    }

}
