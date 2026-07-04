package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;

public class StatusWidget {

    private int x, y, w, h;
    private String status = "";
    private StatusType type = StatusType.INFO;

    public enum StatusType { INFO, SUCCESS, ERROR, LOADING }

    public StatusWidget(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        if (status.isEmpty()) return;
        PanelRenderer.render(gui, x, y, w, h);
        int color = switch (type) {
            case SUCCESS -> TextRenderer.COLOR_SUCCESS;
            case ERROR -> TextRenderer.COLOR_ERROR;
            case LOADING -> TextRenderer.COLOR_INFO;
            default -> TextRenderer.COLOR_GRAY;
        };
        gui.drawString(net.minecraft.client.Minecraft.getInstance().font, status, x + 4, y + 4, color, false);
    }

    public void setStatus(String status, StatusType type) {
        this.status = status;
        this.type = type;
    }

    public void clear() {
        this.status = "";
    }

}
