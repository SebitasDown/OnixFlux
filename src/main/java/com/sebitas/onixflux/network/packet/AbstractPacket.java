package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.NetworkValidation;
import com.sebitas.onixflux.network.PacketContext;
import net.minecraft.network.FriendlyByteBuf;

public abstract class AbstractPacket {

    public abstract void encode(FriendlyByteBuf buf);

    public abstract void decode(FriendlyByteBuf buf);

    public abstract void handle(PacketContext ctx);

    public boolean validate() {
        return true;
    }

}
