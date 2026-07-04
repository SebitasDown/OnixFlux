package com.sebitas.onixflux.client.gui.animation;

public class SlideAnimation extends Animation {

    private final float fromX;
    private final float fromY;
    private final float toX;
    private final float toY;

    public SlideAnimation(int duration, float fromX, float fromY, float toX, float toY) {
        super(duration, Easing::easeOutCubic);
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public float x() {
        return Easing.lerp(fromX, toX, eased());
    }

    public float y() {
        return Easing.lerp(fromY, toY, eased());
    }

}
