package com.sebitas.onixflux.network.codec;

import net.minecraft.network.FriendlyByteBuf;

public interface PacketSerializer<T> {

    void serialize(T object, FriendlyByteBuf buf);

    T deserialize(FriendlyByteBuf buf);

}
