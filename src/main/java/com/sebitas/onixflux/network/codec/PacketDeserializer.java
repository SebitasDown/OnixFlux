package com.sebitas.onixflux.network.codec;

import com.sebitas.onixflux.network.NetworkException;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public final class PacketDeserializer {

    private PacketDeserializer() {}

    public static <T> T read(FriendlyByteBuf buf, Supplier<T> factory, Reader<T> reader) {
        T instance = factory.get();
        try {
            reader.read(buf, instance);
            return instance;
        } catch (Exception e) {
            throw new NetworkException("Failed to deserialize packet", e);
        }
    }

    @FunctionalInterface
    public interface Reader<T> {
        void read(FriendlyByteBuf buf, T instance) throws Exception;
    }

}
