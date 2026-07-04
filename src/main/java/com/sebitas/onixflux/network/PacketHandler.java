package com.sebitas.onixflux.network;

import com.sebitas.onixflux.network.packet.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public final class PacketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger("PacketHandler");

    private PacketHandler() {}

    public static <T extends AbstractPacket> void handle(T packet, Supplier<NetworkEvent.Context> ctx) {
        long start = System.nanoTime();
        try {
            PacketContext context = new PacketContext(ctx);
            packet.handle(context);
            ctx.get().setPacketHandled(true);
        } catch (Exception e) {
            NetworkDiagnostics.recordError();
            LOGGER.error("Error handling packet {}: {}", packet.getClass().getSimpleName(), e.getMessage());
        } finally {
            NetworkDiagnostics.recordHandleTime(System.nanoTime() - start);
            NetworkDiagnostics.recordReceived();
        }
    }

}
