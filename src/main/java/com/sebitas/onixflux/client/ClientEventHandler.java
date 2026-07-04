package com.sebitas.onixflux.client;

import com.sebitas.onixflux.client.gui.FluxTableScreen;
import com.sebitas.onixflux.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "onixflux", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEventHandler {

    private ClientEventHandler() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ClientInitializer.initialize();
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.FLUX_TABLE.get(), FluxTableScreen::new);
        });
    }

}
