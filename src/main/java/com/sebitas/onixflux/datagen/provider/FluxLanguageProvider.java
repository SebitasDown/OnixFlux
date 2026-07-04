package com.sebitas.onixflux.datagen.provider;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Map;

public class FluxLanguageProvider extends LanguageProvider {

    private static final Map<String, String> ENGLISH = Map.ofEntries(
            Map.entry("block.onixflux.flux_table", "Flux Table"),
            Map.entry("container.onixflux.flux_table", "Flux Table"),
            Map.entry("gui.onixflux.search", "Search items..."),
            Map.entry("gui.onixflux.fx", "FX: %s"),
            Map.entry("gui.onixflux.value", "Value: %s FX"),
            Map.entry("gui.onixflux.no_selection", "No selection"),
            Map.entry("gui.onixflux.learn_slot", "Learn"),
            Map.entry("gui.onixflux.output_slot", "Output"),
            Map.entry("gui.onixflux.learn", "Learn"),
            Map.entry("gui.onixflux.transmute", "Transmute"),
            Map.entry("category.onixflux.all", "All"),
            Map.entry("category.onixflux.blocks", "Blocks"),
            Map.entry("category.onixflux.tools", "Tools"),
            Map.entry("category.onixflux.weapons", "Weapons"),
            Map.entry("category.onixflux.armor", "Armor"),
            Map.entry("category.onixflux.food", "Food"),
            Map.entry("category.onixflux.ores", "Ores"),
            Map.entry("category.onixflux.redstone", "Redstone"),
            Map.entry("category.onixflux.nature", "Nature"),
            Map.entry("category.onixflux.misc", "Misc"),
            Map.entry("hud.onixflux.fx", "FX: %s"),
            Map.entry("tooltip.onixflux.fx_value", "FX: %s"),
            Map.entry("tooltip.onixflux.fx_source", "Source: %s"),
            Map.entry("tooltip.onixflux.learned", "Known: %s"),
            Map.entry("tooltip.onixflux.not_learned", "Unknown"),
            Map.entry("key.onixflux.open_hud", "Toggle FX HUD"),
            Map.entry("advancement.onixflux.root", "OnixFlux"),
            Map.entry("advancement.onixflux.root.desc", "The power of Flux"),
            Map.entry("advancement.onixflux.first_learn", "First Knowledge"),
            Map.entry("advancement.onixflux.first_learn.desc", "Learn your first item"),
            Map.entry("advancement.onixflux.first_transmute", "Transmutation"),
            Map.entry("advancement.onixflux.first_transmute.desc", "Transmute your first item")
    );

    public FluxLanguageProvider(PackOutput output) {
        super(output, OnixFlux.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ENGLISH.forEach(this::add);
    }

}
