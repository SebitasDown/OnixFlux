package com.sebitas.onixflux.client.gui.animation;

public final class Easing {

    private Easing() {}

    public static float linear(float t) {
        return t;
    }

    public static float easeInQuad(float t) {
        return t * t;
    }

    public static float easeOutQuad(float t) {
        return t * (2 - t);
    }

    public static float easeInOutQuad(float t) {
        return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }

    public static float easeInCubic(float t) {
        return t * t * t;
    }

    public static float easeOutCubic(float t) {
        return (--t) * t * t + 1;
    }

    public static float easeInOutCubic(float t) {
        return t < 0.5f ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;
    }

    public static float easeOutElastic(float t) {
        if (t == 0 || t == 1) return t;
        return (float) (Math.pow(2, -10 * t) * Math.sin((t - 0.075f) * (2 * Math.PI) / 0.3f) + 1);
    }

    public static float easeOutBack(float t) {
        float c1 = 1.70158f;
        float c3 = c1 + 1;
        return (float) (1 + c3 * Math.pow(t - 1, 3) + c1 * Math.pow(t - 1, 2));
    }

    public static float easeOutBounce(float t) {
        if (t < 1 / 2.75f) {
            return 7.5625f * t * t;
        } else if (t < 2 / 2.75f) {
            return 7.5625f * (t -= 1.5f / 2.75f) * t + 0.75f;
        } else if (t < 2.5f / 2.75f) {
            return 7.5625f * (t -= 2.25f / 2.75f) * t + 0.9375f;
        } else {
            return 7.5625f * (t -= 2.625f / 2.75f) * t + 0.984375f;
        }
    }

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    public static int lerpColor(int a, int b, float t) {
        int ar = (a >> 16) & 0xFF;
        int ag = (a >> 8) & 0xFF;
        int ab = a & 0xFF;
        int aa = (a >> 24) & 0xFF;
        int br = (b >> 16) & 0xFF;
        int bg = (b >> 8) & 0xFF;
        int bb = b & 0xFF;
        int ba = (b >> 24) & 0xFF;
        int r = (int) lerp(ar, br, t);
        int g = (int) lerp(ag, bg, t);
        int b_ = (int) lerp(ab, bb, t);
        int a_ = (int) lerp(aa, ba, t);
        return (a_ << 24) | (r << 16) | (g << 8) | b_;
    }

}
