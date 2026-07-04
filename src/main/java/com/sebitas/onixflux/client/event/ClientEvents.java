package com.sebitas.onixflux.client.event;

import com.sebitas.onixflux.client.ClientInitializer;
import com.sebitas.onixflux.client.ClientManager;
import com.sebitas.onixflux.client.ClientRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public final class ClientEvents {

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (ClientRegistry.TOGGLE_HUD.consumeClick()) {
            ClientManager.toggleOverlay();
        }
    }

}
