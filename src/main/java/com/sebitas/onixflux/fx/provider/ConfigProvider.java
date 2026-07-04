package com.sebitas.onixflux.fx.provider;

import com.sebitas.onixflux.fx.FluxLoader;

public final class ConfigProvider implements FluxValueProvider {

    @Override
    public void provide() {
        FluxLoader.loadCustomValues();
    }

}
