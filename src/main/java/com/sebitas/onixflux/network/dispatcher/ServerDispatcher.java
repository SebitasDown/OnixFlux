package com.sebitas.onixflux.network.dispatcher;

import com.sebitas.onixflux.network.packet.AbstractPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class ServerDispatcher {

    private ServerDispatcher() {}

    public static void sendToPlayer(ServerPlayer player, AbstractPacket packet) {
        PacketDispatcher.sendToPlayer(player, packet);
    }

    public static void sendToAll(AbstractPacket packet) {
        PacketDispatcher.sendToAllPlayers(packet);
    }

    public static void sendToTracking(Player player, AbstractPacket packet) {
        if (player instanceof ServerPlayer sp) {
            PacketDispatcher.sendToTracking(sp, packet);
        }
    }

}
