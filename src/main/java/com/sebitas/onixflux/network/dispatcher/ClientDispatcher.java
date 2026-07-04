package com.sebitas.onixflux.network.dispatcher;

import com.sebitas.onixflux.network.packet.AbstractPacket;

public final class ClientDispatcher {

    private ClientDispatcher() {}

    public static void sendToServer(AbstractPacket packet) {
        PacketDispatcher.sendToServer(packet);
    }

}
