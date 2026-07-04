package com.sebitas.onixflux.client.gui.tooltip;

import com.sebitas.onixflux.client.gui.render.TextRenderer;
import com.sebitas.onixflux.transmutation.FluxItemEntry;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public final class ItemTooltip {

    private ItemTooltip() {}

    public static List<Component> create(FluxItemEntry entry, boolean isFavourite, boolean isLearned) {
        List<Component> lines = new ArrayList<>();

        lines.add(Component.literal(entry.displayName()).withStyle(s -> s.withColor(TextRenderer.COLOR_WHITE)));

        String modName = entry.modId();
        lines.add(Component.literal("Mod: " + modName).withStyle(s -> s.withColor(TextRenderer.COLOR_GRAY)));

        if (entry.fxValue() > 0) {
            lines.add(Component.literal("FX: " + String.format("%,d", entry.fxValue()) + " EMC")
                    .withStyle(s -> s.withColor(TextRenderer.COLOR_FX)));
        }

        if (isFavourite) {
            lines.add(Component.literal("\u2605 Favourite").withStyle(s -> s.withColor(TextRenderer.COLOR_GOLD)));
        }

        if (!isLearned) {
            lines.add(Component.literal("Not learned").withStyle(s -> s.withColor(TextRenderer.COLOR_ERROR)));
        }

        return lines;
    }

}
