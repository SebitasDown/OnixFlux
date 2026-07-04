package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class TooltipWidget {

    private int x, y, w, h;
    private List<String> lines;
    private boolean visible;

    public TooltipWidget() {
        this.lines = List.of();
        this.visible = false;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY) {
        if (!visible || lines.isEmpty()) return;

        var font = Minecraft.getInstance().font;
        int lineH = font.lineHeight + 2;
        int tw = 0;
        for (String l : lines) tw = Math.max(tw, font.width(l) + 8);
        int th = lines.size() * lineH + 6;

        int tx = mouseX + 12;
        int ty = mouseY - 12 - th;
        int sw = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int sh = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        if (tx + tw > sw) tx = sw - tw - 4;
        if (ty < 0) ty = mouseY + 12;

        gui.pose().pushPose();
        gui.pose().translate(0, 0, 400);

        gui.fill(tx, ty, tx + tw, ty + th, 0xE00A0A0A);
        gui.renderOutline(tx, ty, tw, th, 0xFF1A1A2E);
        gui.fill(tx + 1, ty + 1, tx + tw - 1, ty + 3, 0xFF533483);

        int ly = ty + 4;
        for (String line : lines) {
            gui.drawString(font, line, tx + 4, ly, TextRenderer.COLOR_WHITE, false);
            ly += lineH;
        }

        gui.pose().popPose();
    }

    public void setLines(List<String> lines) { this.lines = lines; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public boolean isVisible() { return visible; }

}
