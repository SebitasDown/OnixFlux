package com.sebitas.onixflux.client.gui.render;

import com.sebitas.onixflux.client.gui.animation.Animation;
import net.minecraft.client.gui.GuiGraphics;

public final class AnimationRenderer {

    private AnimationRenderer() {}

    public static void applyFade(GuiGraphics gui, Animation animation, Runnable renderContent) {
        if (animation == null || !animation.isRunning()) {
            renderContent.run();
            return;
        }
        int alpha = (int) (animation.eased() * 255);
        gui.pose().pushPose();
        gui.pose().translate(0, 0, 100);
        renderContent.run();
        gui.pose().popPose();
    }

    public static void renderGlow(GuiGraphics gui, int x, int y, int w, int h, float alpha) {
        if (alpha <= 0) return;
        int a = (int) (alpha * 80);
        gui.fill(x, y, x + w, y + h, (a << 24) | 0x533483);
        gui.renderOutline(x, y, w, h, ((int) (alpha * 200) << 24) | 0x533483);
    }

}
