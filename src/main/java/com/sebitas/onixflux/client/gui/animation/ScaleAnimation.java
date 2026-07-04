package com.sebitas.onixflux.client.gui.animation;

public class ScaleAnimation extends Animation {

    private final float from;
    private final float to;

    public ScaleAnimation(int duration, float from, float to) {
        super(duration, Easing::easeOutBack);
        this.from = from;
        this.to = to;
    }

    public float scale() {
        return Easing.lerp(from, to, eased());
    }

}
