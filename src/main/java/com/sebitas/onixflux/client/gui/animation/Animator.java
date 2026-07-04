package com.sebitas.onixflux.client.gui.animation;

public final class Animator {

    private float value;
    private float target;
    private float speed;
    private boolean active;

    public Animator(float initial, float speed) {
        this.value = initial;
        this.target = initial;
        this.speed = speed;
        this.active = false;
    }

    public void tick() {
        if (!active) return;
        if (Math.abs(value - target) < 0.5f) {
            value = target;
            active = false;
            return;
        }
        value = Easing.lerp(value, target, speed);
    }

    public void setTarget(float target) {
        if (Math.abs(this.target - target) > 0.5f) {
            this.target = target;
            this.active = true;
        }
    }

    public void setValue(float value) {
        this.value = value;
        this.target = value;
        this.active = false;
    }

    public float value() {
        return value;
    }

    public float target() {
        return target;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFinished() {
        return !active && Math.abs(value - target) < 0.5f;
    }

}
