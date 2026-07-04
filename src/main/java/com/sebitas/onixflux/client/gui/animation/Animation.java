package com.sebitas.onixflux.client.gui.animation;

public class Animation {

    private final int duration;
    private final EasingFunction easing;
    private int elapsed;
    private boolean running;

    public Animation(int duration, EasingFunction easing) {
        this.duration = duration;
        this.easing = easing;
        this.elapsed = 0;
        this.running = false;
    }

    public void start() {
        elapsed = 0;
        running = true;
    }

    public void stop() {
        running = false;
        elapsed = duration;
    }

    public void tick() {
        if (!running) return;
        elapsed = Math.min(elapsed + 1, duration);
        if (elapsed >= duration) running = false;
    }

    public float progress() {
        return duration > 0 ? Math.min(1.0f, (float) elapsed / duration) : 1.0f;
    }

    public float eased() {
        return easing.apply(progress());
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isFinished() {
        return elapsed >= duration;
    }

    public interface EasingFunction {
        float apply(float t);
    }

}
