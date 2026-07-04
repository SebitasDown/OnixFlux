package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.transmutation.FluxTableLogic;
import com.sebitas.onixflux.transmutation.FluxTableMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class CreateItemPacket extends AbstractPacket implements ClientPacket {

    private int containerId;
    private ResourceLocation itemId;
    private int count;

    public CreateItemPacket() {}

    public CreateItemPacket(int containerId, Item item, int count) {
        this.containerId = containerId;
        this.itemId = BuiltInRegistries.ITEM.getKey(item);
        this.count = count;
    }

    public CreateItemPacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(containerId);
        buf.writeResourceLocation(itemId);
        buf.writeInt(count);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.containerId = buf.readInt();
        this.itemId = buf.readResourceLocation();
        this.count = buf.readInt();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            if (!(player.containerMenu instanceof FluxTableMenu menu)) return;
            if (menu.containerId != containerId) return;
            Item item = BuiltInRegistries.ITEM.get(itemId);
            if (item == null || item == BuiltInRegistries.ITEM.get(BuiltInRegistries.ITEM.getDefaultKey())) return;
            FluxTableLogic.tryTransmute(player, item, count, menu.getBlockEntity().getInventory(), 1);
        });
    }

}
