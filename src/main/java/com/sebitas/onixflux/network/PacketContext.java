package com.sebitas.onixflux.network;

import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public final class PacketContext {

    private final Supplier<NetworkEvent.Context> context;

    public PacketContext(Supplier<NetworkEvent.Context> context) {
        this.context = context;
    }

    public Supplier<NetworkEvent.Context> context() {
        return context;
    }

    public NetworkEvent.Context get() {
        return context.get();
    }

    public boolean isClientSide() {
        return context.get().getDirection().getReceptionSide().isClient();
    }

    public boolean isServerSide() {
        return context.get().getDirection().getReceptionSide().isServer();
    }

    public void enqueueWork(Runnable task) {
        context.get().enqueueWork(task);
    }

    public void setPacketHandled(boolean handled) {
        context.get().setPacketHandled(handled);
    }

}
