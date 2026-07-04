package com.sebitas.onixflux.integration.plugins;

import com.sebitas.onixflux.integration.IntegrationMetadata;
import com.sebitas.onixflux.integration.IntegrationPriority;
import com.sebitas.onixflux.integration.adapter.IntegrationAdapter;

import java.util.Set;

public class CraftTweakerIntegration implements IntegrationAdapter {

    private static final String MOD_ID = "crafttweaker";

    @Override
    public IntegrationMetadata metadata() {
        return new IntegrationMetadata(
                "crafttweaker",
                "CraftTweaker Integration",
                MOD_ID,
                "1.0",
                Set.of(),
                IntegrationPriority.HIGH,
                true,
                true
        );
    }

    @Override
    public void initialize() {
    }

    @Override
    public boolean isActive() {
        return net.minecraftforge.fml.ModList.get().isLoaded(MOD_ID);
    }

}
