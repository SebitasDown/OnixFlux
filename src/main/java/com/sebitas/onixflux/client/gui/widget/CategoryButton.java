package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.WidgetRenderer;
import net.minecraft.client.gui.GuiGraphics;

import java.util.function.Consumer;

public class CategoryButton {

    public final String name;
    public final Consumer<Integer> onSelect;

    public CategoryButton(String name, Consumer<Integer> onSelect) {
        this.name = name;
        this.onSelect = onSelect;
    }

    public void render(GuiGraphics gui, int x, int y, int w, int h, boolean hovered, boolean selected) {
        WidgetRenderer.renderButton(gui, x, y, w, h, hovered, selected);
        int color = selected ? 0xFFAA88FF : (hovered ? 0xFFE0E0E0 : 0xFF888888);
        gui.drawString(net.minecraft.client.Minecraft.getInstance().font, name, x + 4, y + 4, color, false);
    }

}
