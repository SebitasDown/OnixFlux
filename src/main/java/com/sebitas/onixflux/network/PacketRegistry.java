package com.sebitas.onixflux.network;

import com.sebitas.onixflux.network.packet.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class PacketRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger("PacketRegistry");
    private static final List<Class<? extends AbstractPacket>> REGISTRATION_ORDER = new ArrayList<>();
    private static final Map<Class<? extends AbstractPacket>, Integer> CLASS_TO_ID = new LinkedHashMap<>();
    private static final Map<Integer, Function<FriendlyByteBuf, ? extends AbstractPacket>> ID_TO_DECODER = new LinkedHashMap<>();
    private static final Map<Class<? extends AbstractPacket>, BiConsumer<AbstractPacket, FriendlyByteBuf>> CLASS_TO_ENCODER = new LinkedHashMap<>();

    private PacketRegistry() {}

    public static <T extends AbstractPacket> void register(
            Class<T> packetClass,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, FriendlyByteBuf> encoder
    ) {
        int id = REGISTRATION_ORDER.size();
        REGISTRATION_ORDER.add(packetClass);
        CLASS_TO_ID.put(packetClass, id);
        ID_TO_DECODER.put(id, decoder);
        CLASS_TO_ENCODER.put(packetClass, (p, b) -> encoder.accept((T) p, b));
        LOGGER.debug("Registered packet {} with id {}", packetClass.getSimpleName(), id);
    }

    public static int getId(Class<? extends AbstractPacket> packetClass) {
        Integer id = CLASS_TO_ID.get(packetClass);
        if (id == null) throw new NetworkException("Packet not registered: " + packetClass.getSimpleName());
        return id;
    }

    @SuppressWarnings("unchecked")
    public static <T extends AbstractPacket> T create(int id, FriendlyByteBuf buf) {
        Function<FriendlyByteBuf, ?> decoder = ID_TO_DECODER.get(id);
        if (decoder == null) throw new NetworkException("Unknown packet id: " + id);
        return (T) decoder.apply(buf);
    }

    @SuppressWarnings("unchecked")
    public static <T extends AbstractPacket> void encode(T packet, FriendlyByteBuf buf) {
        BiConsumer<AbstractPacket, FriendlyByteBuf> encoder = CLASS_TO_ENCODER.get(packet.getClass());
        if (encoder == null) throw new NetworkException("No encoder for packet: " + packet.getClass().getSimpleName());
        encoder.accept(packet, buf);
    }

    public static int size() {
        return REGISTRATION_ORDER.size();
    }

    public static List<Class<? extends AbstractPacket>> getRegisteredPackets() {
        return Collections.unmodifiableList(REGISTRATION_ORDER);
    }

}
