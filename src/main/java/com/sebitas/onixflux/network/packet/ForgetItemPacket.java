package com.sebitas.onixflux.network.packet;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.player.PlayerDataManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class ForgetItemPacket extends AbstractPacket implements ClientPacket {

    private ResourceLocation itemId;

    public ForgetItemPacket() {}

    public ForgetItemPacket(Item item) {
        this.itemId = BuiltInRegistries.ITEM.getKey(item);
    }

    public ForgetItemPacket(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(itemId);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.itemId = buf.readResourceLocation();
    }

    @Override
    public void handle(PacketContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            Item item = BuiltInRegistries.ITEM.get(itemId);
            if (item == null || item == BuiltInRegistries.ITEM.get(BuiltInRegistries.ITEM.getDefaultKey())) return;
            PlayerDataManager.forget(player, item);
        });
    }

}
