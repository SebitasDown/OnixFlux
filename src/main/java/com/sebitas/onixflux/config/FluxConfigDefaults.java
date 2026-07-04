package com.sebitas.onixflux.config;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.resources.ResourceLocation;

public final class FluxConfigDefaults {

    private FluxConfigDefaults() {}

    public static final String CATEGORY_FX_VALUES = "fx_values";
    public static final String CATEGORY_BEHAVIOR = "behavior";
    public static final String CATEGORY_PERFORMANCE = "performance";
    public static final String CATEGORY_INTEGRATION = "integration";

    public static final boolean DEFAULT_ENABLE_AUTO_RELOAD = true;
    public static final int DEFAULT_AUTO_RELOAD_INTERVAL = 30;
    public static final boolean DEFAULT_ENABLE_DATAPACK_VALUES = true;
    public static final boolean DEFAULT_LOG_REGISTRATION = true;
    public static final int DEFAULT_SEARCH_RESULTS = 36;
    public static final boolean DEFAULT_SHOW_UNLEARNABLE = false;

    public static final long MAX_FX_PER_ITEM = 1_000_000_000L;
    public static final long MIN_FX_PER_ITEM = 1L;
    public static final int MAX_PLAYER_FX = Integer.MAX_VALUE;

    public static final String[] DEFAULT_ENTRIES = {};

    public static ResourceLocation defaultFxPath() {
        return ResourceLocation.fromNamespaceAndPath(OnixFlux.MOD_ID, "fx");
    }

}
