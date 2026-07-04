package com.sebitas.onixflux.fx;

import com.sebitas.onixflux.fx.provider.ApiProvider;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import com.sebitas.onixflux.fx.recipe.FluxRecipeLoader;
import com.sebitas.onixflux.fx.recipe.RecipeNormalizer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public final class FluxLoader {

    private static final ForgeConfigSpec CONFIG_SPEC;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_VALUES;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        CUSTOM_VALUES = builder
                .comment(
                        "Custom FX values for items.",
                        "Format: \"modid:item_path=value\"",
                        "Example: \"minecraft:diamond=16384\""
                )
                .defineListAllowEmpty("custom_values", List.of(), FluxLoader::validateEntry);
        CONFIG_SPEC = builder.build();
    }

    private FluxLoader() {
    }

    public static ForgeConfigSpec configSpec() {
        return CONFIG_SPEC;
    }

    public static void loadCustomValues() {
        List<? extends String> entries = CUSTOM_VALUES.get();
        for (String entry : entries) {
            String[] parts = entry.split("=", 2);
            if (parts.length != 2) continue;

            String itemKey = parts[0].trim();
            String valueStr = parts[1].trim();

            ResourceLocation id = ResourceLocation.tryParse(itemKey);
            if (id == null) {
                FluxEngine.logger().warn("Invalid item ID in config: {}", itemKey);
                continue;
            }

            Item item = ForgeRegistries.ITEMS.getValue(id);
            if (item == null || item == Items.AIR) {
                FluxEngine.logger().warn("Unknown item in config: {}", itemKey);
                continue;
            }

            try {
                long value = Long.parseLong(valueStr);
                FluxEngine.register(item, value, FluxSource.CONFIG);
            } catch (NumberFormatException e) {
                FluxEngine.logger().warn("Invalid FX value in config for {}: {}", itemKey, valueStr);
            }
        }
    }

    public static List<FluxRecipe> loadRecipes(RecipeManager recipeManager, RegistryAccess registryAccess) {
        List<FluxRecipe> raw = FluxRecipeLoader.loadAll(recipeManager, registryAccess);
        return RecipeNormalizer.normalize(raw);
    }

    public static void loadApiValues() {
        new ApiProvider().provide();
    }

    private static boolean validateEntry(Object obj) {
        if (!(obj instanceof String s)) return false;
        return s.contains("=");
    }

}
