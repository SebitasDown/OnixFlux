package com.sebitas.onixflux.client.gui.animation;

public class FadeAnimation extends Animation {

    private final float from;
    private final float to;

    public FadeAnimation(int duration, float from, float to) {
        super(duration, Easing::easeOutQuad);
        this.from = from;
        this.to = to;
    }

    public float alpha() {
        return Easing.lerp(from, to, eased());
    }

}
