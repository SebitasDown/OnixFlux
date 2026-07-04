package com.sebitas.onixflux.network;

import net.minecraft.network.FriendlyByteBuf;

@FunctionalInterface
public interface PacketCodec<T> {

    T decode(FriendlyByteBuf buf);

    default void encode(T packet, FriendlyByteBuf buf) {
    }

}
