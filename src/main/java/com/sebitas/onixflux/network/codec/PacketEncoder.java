package com.sebitas.onixflux.network.codec;

import com.sebitas.onixflux.network.NetworkException;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

public final class PacketEncoder {

    private PacketEncoder() {}

    public static FriendlyByteBuf encode(EncodeFunction encoder) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        try {
            encoder.encode(buf);
            return buf;
        } catch (Exception e) {
            buf.release();
            throw new NetworkException("Failed to encode packet", e);
        }
    }

    @FunctionalInterface
    public interface EncodeFunction {
        void encode(FriendlyByteBuf buf) throws Exception;
    }

}
