package com.sebitas.onixflux.network.codec;

import com.sebitas.onixflux.network.NetworkException;
import net.minecraft.network.FriendlyByteBuf;

public final class PacketDecoder {

    private PacketDecoder() {}

    public static <T> T decode(FriendlyByteBuf buf, DecodeFunction<T> decoder) {
        try {
            return decoder.decode(buf);
        } catch (Exception e) {
            throw new NetworkException("Failed to decode packet", e);
        }
    }

    @FunctionalInterface
    public interface DecodeFunction<T> {
        T decode(FriendlyByteBuf buf) throws Exception;
    }

}
