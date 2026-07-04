package com.sebitas.onixflux.network.listener;

import com.sebitas.onixflux.network.packet.*;

public interface ClientPacketListener extends PacketListener {

    void onSyncFlux(SyncFluxPacket packet);

    void onSyncKnowledge(SyncKnowledgePacket packet);

    void onOpenFluxTable(OpenFluxTablePacket packet);

    void onConsumeFlux(ConsumeFluxPacket packet);

    void onReloadFX(ReloadFXPacket packet);

    void onPlayerData(PlayerDataPacket packet);

    void onBlockEntitySync(BlockEntitySyncPacket packet);

}
