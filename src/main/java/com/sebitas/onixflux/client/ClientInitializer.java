package com.sebitas.onixflux.client;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.client.event.ClientEvents;
import com.sebitas.onixflux.client.hud.FXHudOverlay;
import com.sebitas.onixflux.client.tooltip.FXTooltipHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OnixFlux.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientInitializer {

    private ClientInitializer() {}

    public static void initialize() {
        ClientRegistry.registerAll();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(FXTooltipHandler.class);
    }

}
