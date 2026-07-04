package com.sebitas.onixflux.client.gui.redesign;

public final class FluxTheme {

    private FluxTheme() {}

    public static final int BLACK = 0xFF050505;
    public static final int DARK_GRAY = 0xFF111111;
    public static final int DARK_PANEL = 0xE0080808;
    public static final int BLUE = 0xFF2B8FFF;
    public static final int PURPLE = 0xFF8D4BFF;
    public static final int CYAN = 0xFF00D4FF;
    public static final int GLOW = 0xFFC38FFF;

    public static final int BLUE_DIM = 0x402B8FFF;
    public static final int PURPLE_DIM = 0x408D4BFF;
    public static final int CYAN_DIM = 0x4000D4FF;
    public static final int GLOW_DIM = 0x40C38FFF;

    public static final int BLUE_FAINT = 0x152B8FFF;
    public static final int PURPLE_FAINT = 0x158D4BFF;

    public static final int TEXT_WHITE = 0xFFE0E0E0;
    public static final int TEXT_DIM = 0xFF808080;
    public static final int TEXT_BRIGHT = 0xFFFFFFFF;

    public static final int SCREEN_W = 320;
    public static final int SCREEN_H = 240;

    public static final int HEADER_Y = 4;
    public static final int HEADER_H = 14;
    public static final int SEARCH_Y = 20;
    public static final int SEARCH_H = 16;
    public static final int FX_BAR_Y = 38;
    public static final int FX_BAR_H = 14;

    public static final int LEFT_ZONE_X = 4;
    public static final int LEFT_ZONE_W = 150;
    public static final int MACHINE_CX = 79;
    public static final int MACHINE_CY = 110;
    public static final int MACHINE_RADIUS = 50;

    public static final int RIGHT_ZONE_X = 160;
    public static final int RIGHT_ZONE_W = 156;
    public static final int RING_CX = 238;
    public static final int RING_CY = 108;
    public static final int RING_RADIUS = 52;

    public static final int ITEMS_PER_PAGE = 12;
    public static final int SLOT_SIZE = 20;

    public static final int BOTTOM_Y = 216;
    public static final int BOTTOM_H = 20;

    public static final float ANIM_SPEED = 0.12f;
    public static final float HOVER_SCALE = 1.25f;
    public static final float IDLE_BOB = 1.5f;
    public static final float IDLE_ROTATION = 2.0f;

    public static int rgba(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int withAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0x00FFFFFF);
    }
}
