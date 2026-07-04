package com.sebitas.onixflux.network.listener;

import com.sebitas.onixflux.network.PacketContext;
import com.sebitas.onixflux.network.packet.AbstractPacket;

public interface PacketListener {

    void onPacketReceived(AbstractPacket packet, PacketContext ctx);

}
