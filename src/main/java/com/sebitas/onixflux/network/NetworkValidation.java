package com.sebitas.onixflux.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Objects;

public final class NetworkValidation {

    private NetworkValidation() {}

    public static void checkSide(boolean isClientExpected, boolean isClient) {
        if (isClientExpected != isClient) {
            throw new NetworkException("Packet received on wrong side");
        }
    }

    public static void checkBuffer(FriendlyByteBuf buf) {
        Objects.requireNonNull(buf, "Buffer cannot be null");
    }

    public static void checkItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
    }

    public static void checkResourceLocation(ResourceLocation id) {
        Objects.requireNonNull(id, "ResourceLocation cannot be null");
    }

    public static void checkPositive(long value) {
        if (value <= 0) throw new NetworkException("Value must be positive: " + value);
    }

    public static void checkNonNegative(long value) {
        if (value < 0) throw new NetworkException("Value cannot be negative: " + value);
    }

    public static void checkNotBlank(String s, String name) {
        if (s == null || s.isBlank()) throw new NetworkException(name + " cannot be null or blank");
    }

    public static void checkVersion(String version) {
        if (!NetworkVersion.isCompatible(version)) {
            throw new NetworkException("Incompatible network version: " + version + " (expected " + NetworkVersion.version() + ")");
        }
    }

}
