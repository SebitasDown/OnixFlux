package com.sebitas.onixflux.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
public final class ClientCache {

    private static final Map<String, Object> cache = new ConcurrentHashMap<>();

    private ClientCache() {}

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) cache.get(key);
    }

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static boolean has(String key) {
        return cache.containsKey(key);
    }

    public static void remove(String key) {
        cache.remove(key);
    }

    public static void clear() {
        cache.clear();
    }

}
