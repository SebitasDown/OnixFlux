package com.sebitas.onixflux.integration.plugins;

import com.sebitas.onixflux.integration.IntegrationMetadata;
import com.sebitas.onixflux.integration.IntegrationPriority;
import com.sebitas.onixflux.integration.adapter.IntegrationAdapter;

import java.util.Set;

public class CuriosIntegration implements IntegrationAdapter {

    private static final String MOD_ID = "curios";

    @Override
    public IntegrationMetadata metadata() {
        return new IntegrationMetadata(
                "curios",
                "Curios Integration",
                MOD_ID,
                "1.0",
                Set.of(),
                IntegrationPriority.NORMAL,
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
