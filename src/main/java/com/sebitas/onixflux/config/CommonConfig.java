package com.sebitas.onixflux.config;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public final class CommonConfig {

    private CommonConfig() {}

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_VALUES;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("FX value overrides. Format: \"modid:item_path=value\"")
                .push("fx_values");
        CUSTOM_VALUES = builder
                .defineListAllowEmpty("custom_values", List.of(), CommonConfig::validateEntry);
        builder.pop();

        SPEC = builder.build();
    }

    private static boolean validateEntry(Object obj) {
        if (!(obj instanceof String s)) return false;
        return s.contains("=");
    }

}
