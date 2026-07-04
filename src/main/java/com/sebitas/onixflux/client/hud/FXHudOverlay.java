package com.sebitas.onixflux.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sebitas.onixflux.client.ClientConstants;
import com.sebitas.onixflux.client.ClientManager;
import com.sebitas.onixflux.client.ClientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

@OnlyIn(Dist.CLIENT)
public final class FXHudOverlay implements IGuiOverlay {

    public static final FXHudOverlay INSTANCE = new FXHudOverlay();

    private FXHudOverlay() {}

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        if (!ClientManager.isOverlayVisible()) return;
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.isPaused()) return;

        RenderSystem.enableBlend();

        long fx = ClientRenderer.getPlayerFX();
        String text = "FX: " + fx;

        int padding = 4;
        int x = screenWidth - mc.font.width(text) - padding * 2 - 2;
        int y = padding + 2;
        int w = mc.font.width(text) + padding * 2;
        int h = mc.font.lineHeight + padding;

        graphics.fill(x, y, x + w, y + h, ClientConstants.HUD_BACKGROUND);
        graphics.renderOutline(x, y, w, h, ClientConstants.HUD_BORDER);
        graphics.drawString(mc.font, text, x + padding, y + padding, ClientConstants.HUD_COLOR, false);

        RenderSystem.disableBlend();
    }

}
