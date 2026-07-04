package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public class BlockEntitySyncPacket extends AbstractPacket implements ServerPacket {

    private BlockPos pos;
    private CompoundTag data;

    public BlockEntitySyncPacket() {}

    public BlockEntitySyncPacket(BlockPos pos, CompoundTag data) {
        this.pos = pos;
        this.data = data;
    }

    public BlockEntitySyncPacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(data);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.data = buf.readNbt();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            if (pos == null || data == null) return;
            var level = net.minecraft.client.Minecraft.getInstance().level;
            if (level == null) return;
            var be = level.getBlockEntity(pos);
            if (be != null) {
                be.load(data);
            }
        });
    }

}
