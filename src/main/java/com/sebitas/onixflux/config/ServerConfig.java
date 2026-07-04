package com.sebitas.onixflux.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ServerConfig {

    private ServerConfig() {}

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_RELOAD;
    public static final ForgeConfigSpec.IntValue AUTO_RELOAD_INTERVAL;
    public static final ForgeConfigSpec.BooleanValue ENABLE_DATAPACK_VALUES;
    public static final ForgeConfigSpec.BooleanValue LOG_REGISTRATION;
    public static final ForgeConfigSpec.ConfigValue<Integer> SEARCH_RESULTS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Server-side behavior settings").push("behavior");
        ENABLE_AUTO_RELOAD = builder
                .comment("Enable automatic config reload")
                .define("enable_auto_reload", FluxConfigDefaults.DEFAULT_ENABLE_AUTO_RELOAD);
        AUTO_RELOAD_INTERVAL = builder
                .comment("Auto-reload interval in seconds (0 to disable)")
                .defineInRange("auto_reload_interval", FluxConfigDefaults.DEFAULT_AUTO_RELOAD_INTERVAL, 0, 300);
        LOG_REGISTRATION = builder
                .comment("Log every FX registration")
                .define("log_registration", FluxConfigDefaults.DEFAULT_LOG_REGISTRATION);
        SEARCH_RESULTS = builder
                .comment("Maximum search results per page")
                .define("search_results", FluxConfigDefaults.DEFAULT_SEARCH_RESULTS, v -> (int) v >= 1 && (int) v <= 256);
        builder.pop();

        builder.comment("Data pack settings").push("datapack");
        ENABLE_DATAPACK_VALUES = builder
                .comment("Load FX values from data packs")
                .define("enable_datapack_values", FluxConfigDefaults.DEFAULT_ENABLE_DATAPACK_VALUES);
        builder.pop();

        SPEC = builder.build();
    }

}
