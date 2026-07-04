package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.player.PlayerCapabilityAttacher;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class PlayerDataPacket extends AbstractPacket implements ServerPacket {

    private CompoundTag data;

    public PlayerDataPacket() {}

    public PlayerDataPacket(CompoundTag data) {
        this.data = data;
    }

    public PlayerDataPacket(FriendlyByteBuf buf) {
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
            player.getCapability(PlayerCapabilityAttacher.PLAYER_DATA)
                    .ifPresent(cap -> cap.deserializeNBT(data));
        });
    }

}
