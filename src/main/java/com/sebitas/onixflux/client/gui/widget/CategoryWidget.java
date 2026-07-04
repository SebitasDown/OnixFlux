package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;
import java.util.function.Consumer;

public class CategoryWidget {

    private final List<CategoryButton> buttons;
    private int selected = 0;
    private int x, y, w, h;

    public CategoryWidget(int x, int y, int w, List<String> categories, Consumer<Integer> onSelect) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.buttons = categories.stream()
                .map(name -> new CategoryButton(name, onSelect))
                .toList();
        this.h = buttons.size() * 20 + 4;
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        PanelRenderer.render(gui, x, y, w, h);
        int by = y + 2;
        for (int i = 0; i < buttons.size(); i++) {
            CategoryButton btn = buttons.get(i);
            boolean hovered = mouseX >= x + 2 && mouseX <= x + w - 2 && mouseY >= by && mouseY <= by + 16;
            btn.render(gui, x + 2, by, w - 4, 16, hovered, i == selected);
            by += 20;
        }
    }

    public boolean mouseClicked(double mx, double my, int button) {
        int by = y + 2;
        for (int i = 0; i < buttons.size(); i++) {
            if (mx >= x + 2 && mx <= x + w - 2 && my >= by && my <= by + 16) {
                selected = i;
                buttons.get(i).onSelect.accept(i);
                return true;
            }
            by += 20;
        }
        return false;
    }

    public int selected() {
        return selected;
    }

    public void setSelected(int index) {
        if (index >= 0 && index < buttons.size()) selected = index;
    }

}
