package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class SyncFluxPacket extends AbstractPacket implements ServerPacket {

    private long flux;

    public SyncFluxPacket() {}

    public SyncFluxPacket(long flux) {
        this.flux = flux;
    }

    public SyncFluxPacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(flux);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.flux = buf.readLong();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            player.getCapability(com.sebitas.onixflux.player.PlayerCapabilityAttacher.PLAYER_DATA)
                    .ifPresent(cap -> cap.setFlux(flux));
        });
    }

    public long getFlux() {
        return flux;
    }

}
