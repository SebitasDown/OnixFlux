package com.sebitas.onixflux.api.event;

import com.sebitas.onixflux.api.FluxRecipe;
import net.minecraftforge.eventbus.api.Event;

public class FluxRecipeRegisteredEvent extends Event {

    private final FluxRecipe recipe;

    public FluxRecipeRegisteredEvent(FluxRecipe recipe) {
        this.recipe = recipe;
    }

    public FluxRecipe getRecipe() {
        return recipe;
    }

}
