package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class ItemInfoWidget {

    private int x, y, w, h;
    private String displayName = "";
    private String modName = "";
    private String fxValue = "";
    private List<String> tags = List.of();

    public ItemInfoWidget(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);
        int ly = y + 4;
        if (!displayName.isEmpty()) {
            gui.drawString(net.minecraft.client.Minecraft.getInstance().font, displayName, x + 4, ly, TextRenderer.COLOR_WHITE, false);
            ly += 10;
        }
        if (!modName.isEmpty()) {
            gui.drawString(net.minecraft.client.Minecraft.getInstance().font, "Mod: " + modName, x + 4, ly, TextRenderer.COLOR_GRAY, false);
            ly += 10;
        }
        if (!fxValue.isEmpty()) {
            gui.drawString(net.minecraft.client.Minecraft.getInstance().font, "FX: " + fxValue, x + 4, ly, TextRenderer.COLOR_FX, false);
            ly += 10;
        }
        if (!tags.isEmpty()) {
            gui.drawString(net.minecraft.client.Minecraft.getInstance().font, "Tags: " + String.join(", ", tags), x + 4, ly, TextRenderer.COLOR_INFO, false);
        }
    }

    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setModName(String modName) { this.modName = modName; }
    public void setFxValue(long value) { this.fxValue = String.format("%,d", value) + " EMC"; }
    public void setTags(List<String> tags) { this.tags = tags; }

}
