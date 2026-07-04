package com.sebitas.onixflux.network.listener;

import com.sebitas.onixflux.network.packet.*;

public interface ServerPacketListener extends PacketListener {

    void onLearnItem(LearnItemPacket packet);

    void onForgetItem(ForgetItemPacket packet);

    void onCreateItem(CreateItemPacket packet);

}
