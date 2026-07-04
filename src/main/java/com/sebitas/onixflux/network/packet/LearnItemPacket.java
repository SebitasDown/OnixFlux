package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.transmutation.FluxTableLogic;
import com.sebitas.onixflux.transmutation.FluxTableMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class LearnItemPacket extends AbstractPacket implements ClientPacket {

    private int containerId;

    public LearnItemPacket() {}

    public LearnItemPacket(int containerId) {
        this.containerId = containerId;
    }

    public LearnItemPacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(containerId);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.containerId = buf.readInt();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            if (player.containerMenu instanceof FluxTableMenu menu && menu.containerId == containerId) {
                var inv = menu.getBlockEntity().getInventory();
                var stack = inv.getStackInSlot(0);
                FluxTableLogic.tryLearn(player, stack, inv, 0);
            }
        });
    }

}
