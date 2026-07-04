package com.sebitas.onixflux.client;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.player.PlayerDataManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientRenderer {

    private ClientRenderer() {}

    public static long getPlayerFX() {
        var mc = Minecraft.getInstance();
        if (mc.player == null) return 0;
        return PlayerDataManager.getFlux(mc.player);
    }

    public static String getItemFXDisplayName(net.minecraft.world.item.Item item) {
        return FluxEngine.getValue(item)
                .map(v -> v.value() + " FX")
                .orElse("0 FX");
    }

}
