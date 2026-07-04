package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.player.PlayerCapabilityAttacher;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class SyncKnowledgePacket extends AbstractPacket implements ServerPacket {

    private CompoundTag data;

    public SyncKnowledgePacket() {}

    public SyncKnowledgePacket(CompoundTag data) {
        this.data = data;
    }

    public SyncKnowledgePacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(data);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.data = buf.readNbt();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player == null || data == null) return;
            player.getCapability(PlayerCapabilityAttacher.PLAYER_DATA).ifPresent(cap -> {
                CompoundTag current = cap.serializeNBT();
                ListTag learned = data.getList("LearnedItems", Tag.TAG_STRING);
                current.put("LearnedItems", learned);
                cap.deserializeNBT(current);
            });
        });
    }

    public CompoundTag getData() {
        return data;
    }

}
