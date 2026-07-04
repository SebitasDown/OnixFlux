package com.sebitas.onixflux.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ClientConfig {

    private ClientConfig() {}

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue SHOW_HUD;
    public static final ForgeConfigSpec.BooleanValue SHOW_TOOLTIPS;
    public static final ForgeConfigSpec.BooleanValue SHOW_ANIMATIONS;
    public static final ForgeConfigSpec.BooleanValue SHOW_PARTICLES;
    public static final ForgeConfigSpec.DoubleValue HUD_SCALE;
    public static final ForgeConfigSpec.IntValue HUD_X_OFFSET;
    public static final ForgeConfigSpec.IntValue HUD_Y_OFFSET;
    public static final ForgeConfigSpec.BooleanValue COLORED_TOOLTIPS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("HUD settings").push("hud");
        SHOW_HUD = builder.define("show_hud", true);
        HUD_SCALE = builder.defineInRange("hud_scale", 1.0, 0.5, 2.0);
        HUD_X_OFFSET = builder.defineInRange("hud_x_offset", 0, -500, 500);
        HUD_Y_OFFSET = builder.defineInRange("hud_y_offset", 0, -500, 500);
        builder.pop();

        builder.comment("Tooltip settings").push("tooltips");
        SHOW_TOOLTIPS = builder.define("show_tooltips", true);
        COLORED_TOOLTIPS = builder.define("colored_tooltips", true);
        builder.pop();

        builder.comment("Visual effects").push("visuals");
        SHOW_ANIMATIONS = builder.define("show_animations", true);
        SHOW_PARTICLES = builder.define("show_particles", true);
        builder.pop();

        SPEC = builder.build();
    }

}
