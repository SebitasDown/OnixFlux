package com.sebitas.onixflux.client.tooltip;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record FXTooltip(long fxValue, String source) {

    public String formattedValue() {
        return "FX: " + fxValue;
    }

    public String formattedSource() {
        return "Source: " + source;
    }

}
