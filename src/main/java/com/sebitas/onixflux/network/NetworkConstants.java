package com.sebitas.onixflux.network;

import net.minecraft.resources.ResourceLocation;

public final class NetworkConstants {

    public static final String MOD_ID = "onixflux";
    public static final String CHANNEL_NAME = "main";
    public static final ResourceLocation CHANNEL_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, CHANNEL_NAME);
    public static final String PROTOCOL_VERSION = NetworkVersion.version();
    public static final int MAX_PACKET_SIZE = 32767;

    private NetworkConstants() {}

}
