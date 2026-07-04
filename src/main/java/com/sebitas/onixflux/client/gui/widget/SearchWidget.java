package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.IconRenderer;
import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class SearchWidget {

    private final EditBox box;
    private int x, y, w, h;
    private boolean active;

    public SearchWidget(int x, int y, int w, Consumer<String> onChange) {
        var font = Minecraft.getInstance().font;
        this.box = new EditBox(font, x + 16, y + 2, w - 18, 12, Component.translatable("gui.onixflux.search"));
        this.box.setMaxLength(64);
        this.box.setBordered(false);
        this.box.setTextColor(0xFFE0E0E0);
        this.box.setResponder(onChange);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = 14;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.renderAccented(gui, x, y, w, h);
        IconRenderer.renderSearchIcon(gui, x + 2, y + 1);
        gui.fill(x + 14, y + 1, x + 15, y + h - 1, 0xFF1A1A2E);
        box.render(gui, mouseX, mouseY, partialTick);
    }

    public boolean mouseClicked(double mx, double my, int button) {
        box.mouseClicked(mx, my, button);
        return isHovered(mx, my);
    }

    public boolean keyPressed(int key, int scan, int mods) {
        return box.keyPressed(key, scan, mods);
    }

    public boolean charTyped(char ch, int mods) {
        return box.charTyped(ch, mods);
    }

    public void setFocus(boolean focused) {
        box.setFocused(focused);
    }

    public boolean isHovered(double mx, double my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public String value() {
        return box.getValue();
    }

    public void clear() {
        box.setValue("");
    }

}
