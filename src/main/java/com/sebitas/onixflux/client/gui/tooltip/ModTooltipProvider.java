package com.sebitas.onixflux.client.gui.tooltip;

import com.sebitas.onixflux.OnixFlux;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OnixFlux.MOD_ID, value = Dist.CLIENT)
public final class ModTooltipProvider {

    private ModTooltipProvider() {}

    @SubscribeEvent
    public static void onTooltipColor(RenderTooltipEvent.Color event) {
        event.setBackgroundStart(0xE00A0A0A);
        event.setBackgroundEnd(0xE00A0A0A);
        event.setBorderStart(0xFF1A1A2E);
        event.setBorderEnd(0xFF1A1A2E);
    }

}
