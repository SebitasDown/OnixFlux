package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.transmutation.FluxTableManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class OpenFluxTablePacket extends AbstractPacket implements ServerPacket {

    private BlockPos pos;

    public OpenFluxTablePacket() {}

    public OpenFluxTablePacket(BlockPos pos) {
        this.pos = pos;
    }

    public OpenFluxTablePacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            FluxTableManager.openTable(player, pos);
        });
    }

}
