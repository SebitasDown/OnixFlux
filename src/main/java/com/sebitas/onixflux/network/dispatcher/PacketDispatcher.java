package com.sebitas.onixflux.network.dispatcher;

import com.sebitas.onixflux.network.NetworkException;
import com.sebitas.onixflux.network.packet.AbstractPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketDispatcher {

    private static SimpleChannel channel;

    private PacketDispatcher() {}

    public static void initialize(SimpleChannel ch) {
        channel = ch;
    }

    private static SimpleChannel channel() {
        if (channel == null) throw new NetworkException("PacketDispatcher not initialized");
        return channel;
    }

    public static void sendToServer(AbstractPacket packet) {
        channel().sendToServer(packet);
    }

    public static void sendToPlayer(ServerPlayer player, AbstractPacket packet) {
        channel().send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToAllPlayers(AbstractPacket packet) {
        channel().send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToTracking(Player tracked, AbstractPacket packet) {
        if (tracked instanceof ServerPlayer sp) {
            channel().send(PacketDistributor.TRACKING_ENTITY.with(() -> sp), packet);
        }
    }

    public static void sendToDimension(AbstractPacket packet) {
        channel().send(PacketDistributor.DIMENSION.with(() -> null), packet);
    }

    public static void sendToChunk(AbstractPacket packet) {
        channel().send(PacketDistributor.NEAR.with(() -> null), packet);
    }

}
