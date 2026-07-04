package com.sebitas.onixflux.config;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

public final class ConfigValidator {

    private ConfigValidator() {}

    public static void validateItemKey(String key) {
        if (key == null || key.isBlank()) {
            FluxConfigDiagnostics.recordError();
            throw new FluxConfigException("Item key cannot be null or blank");
        }
        ResourceLocation id = ResourceLocation.tryParse(key);
        if (id == null) {
            FluxConfigDiagnostics.recordError();
            throw new FluxConfigException("Invalid item key format: " + key);
        }
        if (!id.getNamespace().contains(".") && !id.getNamespace().equals("minecraft") && !id.getNamespace().equals(OnixFlux.MOD_ID)) {
            if (!id.getNamespace().matches("[a-z0-9._-]+")) {
                FluxConfigDiagnostics.recordError();
                throw new FluxConfigException("Invalid namespace in item key: " + key);
            }
        }
    }

    public static void validateValue(long value) {
        if (value < FluxConfigDefaults.MIN_FX_PER_ITEM) {
            FluxConfigDiagnostics.recordError();
            throw new FluxConfigException("FX value must be at least " + FluxConfigDefaults.MIN_FX_PER_ITEM + ": " + value);
        }
        if (value > FluxConfigDefaults.MAX_FX_PER_ITEM) {
            FluxConfigDiagnostics.recordWarning();
            throw new FluxConfigException("FX value exceeds maximum " + FluxConfigDefaults.MAX_FX_PER_ITEM + ": " + value);
        }
    }

    public static boolean isValidItem(ResourceLocation id) {
        if (id == null) return false;
        Item item = BuiltInRegistries.ITEM.get(id);
        return item != null && item != Items.AIR;
    }

    public static boolean isValidTagKey(String key) {
        return key != null && key.startsWith("#") && key.length() > 1;
    }

    public static void validateMap(Map<?, ?> map) {
        if (map == null) throw new FluxConfigException("Map cannot be null");
    }

}
