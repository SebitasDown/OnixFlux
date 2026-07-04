package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.player.PlayerCapabilityAttacher;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class ConsumeFluxPacket extends AbstractPacket implements ServerPacket {

    private long amount;

    public ConsumeFluxPacket() {}

    public ConsumeFluxPacket(long amount) {
        this.amount = amount;
    }

    public ConsumeFluxPacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(amount);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.amount = buf.readLong();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            player.getCapability(PlayerCapabilityAttacher.PLAYER_DATA)
                    .ifPresent(cap -> cap.removeFlux(amount));
        });
    }

}
