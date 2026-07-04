package com.sebitas.onixflux.network;

import com.sebitas.onixflux.network.dispatcher.PacketDispatcher;
import com.sebitas.onixflux.network.packet.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;
import java.util.function.Function;

public final class NetworkManager {

    private static final Logger LOGGER = LoggerFactory.getLogger("NetworkManager");
    private static SimpleChannel channel;
    private static boolean initialized = false;

    private NetworkManager() {}

    public static synchronized void initialize() {
        if (initialized) return;

        channel = NetworkRegistry.newSimpleChannel(
                NetworkConstants.CHANNEL_ID,
                () -> NetworkConstants.PROTOCOL_VERSION,
                NetworkVersion::isCompatible,
                NetworkVersion::isCompatible
        );

        registerPackets();
        PacketDispatcher.initialize(channel);

        initialized = true;
        LOGGER.info("NetworkManager initialized with protocol version {}", NetworkConstants.PROTOCOL_VERSION);
    }

    private static <T extends AbstractPacket> void reg(
            Class<T> clazz,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, FriendlyByteBuf> encoder
    ) {
        PacketRegistry.register(clazz, decoder, encoder);
        int idx = PacketRegistry.size() - 1;
        channel.registerMessage(
                idx,
                clazz,
                (p, b) -> {
                    NetworkDiagnostics.recordSent();
                    long start = System.nanoTime();
                    try {
                        encoder.accept(p, b);
                    } finally {
                        NetworkDiagnostics.recordEncodeTime(System.nanoTime() - start);
                    }
                },
                b -> {
                    NetworkDiagnostics.recordReceived();
                    long start = System.nanoTime();
                    try {
                        return decoder.apply(b);
                    } finally {
                        NetworkDiagnostics.recordDecodeTime(System.nanoTime() - start);
                    }
                },
                (p, c) -> PacketHandler.handle(p, c)
        );
    }

    private static void registerPackets() {
        reg(SyncFluxPacket.class, SyncFluxPacket::new, SyncFluxPacket::encode);
        reg(SyncKnowledgePacket.class, SyncKnowledgePacket::new, SyncKnowledgePacket::encode);
        reg(LearnItemPacket.class, LearnItemPacket::new, LearnItemPacket::encode);
        reg(ForgetItemPacket.class, ForgetItemPacket::new, ForgetItemPacket::encode);
        reg(OpenFluxTablePacket.class, OpenFluxTablePacket::new, OpenFluxTablePacket::encode);
        reg(CreateItemPacket.class, CreateItemPacket::new, CreateItemPacket::encode);
        reg(ConsumeFluxPacket.class, ConsumeFluxPacket::new, ConsumeFluxPacket::encode);
        reg(ReloadFXPacket.class, ReloadFXPacket::new, ReloadFXPacket::encode);
        reg(PlayerDataPacket.class, PlayerDataPacket::new, PlayerDataPacket::encode);
        reg(BlockEntitySyncPacket.class, BlockEntitySyncPacket::new, BlockEntitySyncPacket::encode);

        LOGGER.info("Registered {} network packets", PacketRegistry.size());
    }

    public static SimpleChannel channel() {
        if (channel == null) throw new NetworkException("NetworkManager not initialized");
        return channel;
    }

    public static boolean isInitialized() {
        return initialized;
    }

}
