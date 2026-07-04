package com.sebitas.onixflux.client.gui.animation;

public class GlowAnimation extends Animation {

    private static final float GLOW_MIN = 0.3f;
    private static final float GLOW_MAX = 1.0f;

    public GlowAnimation(int duration) {
        super(duration, Easing::easeInOutCubic);
    }

    public float glowAlpha() {
        float p = eased();
        if (p < 0.5f) {
            return Easing.lerp(GLOW_MIN, GLOW_MAX, p * 2);
        } else {
            return Easing.lerp(GLOW_MAX, GLOW_MIN, (p - 0.5f) * 2);
        }
    }

}
