package com.sebitas.onixflux.api;

import com.sebitas.onixflux.api.internal.FluxLookupServiceImpl;
import com.sebitas.onixflux.api.internal.FluxPlayerServiceImpl;
import com.sebitas.onixflux.api.internal.FluxRecipeServiceImpl;
import com.sebitas.onixflux.api.internal.FluxRegistrationServiceImpl;

public final class FluxServices {

    private static FluxRegistrationService registration;
    private static FluxLookupService lookup;
    private static FluxPlayerService player;
    private static FluxRecipeService recipe;

    private FluxServices() {}

    public static synchronized void initialize() {
        if (registration != null) return;
        registration = new FluxRegistrationServiceImpl();
        lookup = new FluxLookupServiceImpl();
        player = new FluxPlayerServiceImpl();
        recipe = new FluxRecipeServiceImpl();
    }

    public static FluxRegistrationService registration() {
        checkInitialized();
        return registration;
    }

    public static FluxLookupService lookup() {
        checkInitialized();
        return lookup;
    }

    public static FluxPlayerService player() {
        checkInitialized();
        return player;
    }

    public static FluxRecipeService recipe() {
        checkInitialized();
        return recipe;
    }

    private static void checkInitialized() {
        if (registration == null) {
            throw new FluxApiException("FluxServices not initialized. Call FluxServices.initialize() during mod construction.");
        }
    }

}
