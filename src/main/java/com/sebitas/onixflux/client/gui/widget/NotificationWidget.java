package com.sebitas.onixflux.client.gui.widget;

import com.sebitas.onixflux.client.gui.animation.FadeAnimation;
import com.sebitas.onixflux.client.gui.render.PanelRenderer;
import com.sebitas.onixflux.client.gui.render.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class NotificationWidget {

    private static final int DURATION = 60;
    private static final int FADE = 10;

    private String message;
    private NotificationType type;
    private FadeAnimation fade;
    private int timer;

    public enum NotificationType { INFO, SUCCESS, ERROR }

    public NotificationWidget() {
        this.message = "";
        this.type = NotificationType.INFO;
        this.fade = new FadeAnimation(FADE, 1.0f, 0.0f);
    }

    public void show(String message, NotificationType type) {
        this.message = message;
        this.type = type;
        this.timer = DURATION;
        this.fade = new FadeAnimation(FADE, 1.0f, 0.0f);
    }

    public void tick() {
        if (timer > 0) {
            timer--;
            if (timer <= FADE && !fade.isRunning()) fade.start();
            if (fade.isRunning()) fade.tick();
        }
    }

    public void render(GuiGraphics gui, int centerX, int y) {
        if (timer <= 0 || message.isEmpty()) return;

        var font = Minecraft.getInstance().font;
        int tw = font.width(message) + 12;
        int th = 14;
        int x = centerX - tw / 2;

        float alpha = fade.isRunning() ? fade.alpha() : 1.0f;
        int bg = (int) (alpha * 224) << 24 | 0x0A0A0A;
        int color = switch (type) {
            case SUCCESS -> TextRenderer.COLOR_SUCCESS;
            case ERROR -> TextRenderer.COLOR_ERROR;
            default -> TextRenderer.COLOR_INFO;
        };

        gui.pose().pushPose();
        gui.pose().translate(0, 0, 500);
        gui.fill(x, y, x + tw, y + th, bg);
        gui.renderOutline(x, y, tw, th, 0xFF1A1A2E);
        gui.drawString(font, message, x + 6, y + 3, color, false);
        gui.pose().popPose();
    }

    public boolean isVisible() { return timer > 0; }

}
