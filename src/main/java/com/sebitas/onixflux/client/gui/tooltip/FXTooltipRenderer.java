package com.sebitas.onixflux.client.gui.tooltip;

import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public final class FXTooltipRenderer {

    private FXTooltipRenderer() {}

    public static void render(GuiGraphics gui, int mouseX, int mouseY, List<String> lines, int maxW) {
        var font = Minecraft.getInstance().font;
        int lineH = font.lineHeight + 2;
        int w = 0;
        for (String l : lines) w = Math.max(w, font.width(l) + 8);
        w = Math.min(w, maxW);
        int h = lines.size() * lineH + 6;

        int tx = mouseX + 12;
        int ty = mouseY - 12 - h;
        int sw = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int sh = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        if (tx + w > sw) tx = sw - w - 4;
        if (ty < 0) ty = mouseY + 12;

        gui.pose().pushPose();
        gui.pose().translate(0, 0, 400);

        gui.fill(tx, ty, tx + w, ty + h, 0xE00A0A0A);
        gui.renderOutline(tx, ty, w, h, 0xFF1A1A2E);
        gui.fill(tx + 1, ty + 1, tx + w - 1, ty + 1 + 1, 0xFF533483);

        int ly = ty + 4;
        for (String line : lines) {
            gui.drawString(font, line, tx + 4, ly, TextRenderer.COLOR_WHITE, false);
            ly += lineH;
        }

        gui.pose().popPose();
    }

}
