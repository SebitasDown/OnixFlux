package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.fx.FluxBootstrap;
import com.sebitas.onixflux.network.PacketContext;
import net.minecraft.network.FriendlyByteBuf;

public class ReloadFXPacket extends AbstractPacket implements ServerPacket {

    public ReloadFXPacket() {}

    public ReloadFXPacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {}

    @Override
    public void decode(FriendlyByteBuf buf) {}

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(FluxBootstrap::reset);
    }

}
