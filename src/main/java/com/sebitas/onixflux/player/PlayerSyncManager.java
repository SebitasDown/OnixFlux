package com.sebitas.onixflux.player;

import com.sebitas.onixflux.network.dispatcher.ServerDispatcher;
import com.sebitas.onixflux.network.packet.PlayerDataPacket;
import com.sebitas.onixflux.network.packet.SyncFluxPacket;
import com.sebitas.onixflux.network.packet.SyncKnowledgePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class PlayerSyncManager {

    private PlayerSyncManager() {}

    public static void syncAll(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        player.getCapability(PlayerCapabilityAttacher.PLAYER_DATA).ifPresent(cap -> {
            CompoundTag data = cap.serializeNBT();
            ServerDispatcher.sendToPlayer(serverPlayer, new PlayerDataPacket(data));
        });
    }

    public static void syncFlux(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        player.getCapability(PlayerCapabilityAttacher.PLAYER_DATA).ifPresent(cap -> {
            ServerDispatcher.sendToPlayer(serverPlayer, new SyncFluxPacket(cap.getFlux()));
        });
    }

    public static void syncKnowledge(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        player.getCapability(PlayerCapabilityAttacher.PLAYER_DATA).ifPresent(cap -> {
            CompoundTag data = cap.serializeNBT();
            CompoundTag knowledgeTag = new CompoundTag();
            knowledgeTag.put("LearnedItems", data.getList("LearnedItems", Tag.TAG_STRING));
            ServerDispatcher.sendToPlayer(serverPlayer, new SyncKnowledgePacket(knowledgeTag));
        });
    }

    public static void syncToAll(Player player) {
        syncAll(player);
    }

}
