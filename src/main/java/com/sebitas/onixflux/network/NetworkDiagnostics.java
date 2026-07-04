package com.sebitas.onixflux.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public final class NetworkDiagnostics {

    private static final Logger LOGGER = LoggerFactory.getLogger("NetworkDiagnostics");

    private static final AtomicLong packetsSent = new AtomicLong();
    private static final AtomicLong packetsReceived = new AtomicLong();
    private static final AtomicLong errors = new AtomicLong();
    private static final AtomicLong totalEncodeTime = new AtomicLong();
    private static final AtomicLong totalDecodeTime = new AtomicLong();
    private static final AtomicLong totalHandleTime = new AtomicLong();

    private NetworkDiagnostics() {}

    public static void recordSent() {
        packetsSent.incrementAndGet();
    }

    public static void recordReceived() {
        packetsReceived.incrementAndGet();
    }

    public static void recordError() {
        errors.incrementAndGet();
    }

    public static void recordEncodeTime(long nanos) {
        totalEncodeTime.addAndGet(nanos);
    }

    public static void recordDecodeTime(long nanos) {
        totalDecodeTime.addAndGet(nanos);
    }

    public static void recordHandleTime(long nanos) {
        totalHandleTime.addAndGet(nanos);
    }

    public static long packetsSent() {
        return packetsSent.get();
    }

    public static long packetsReceived() {
        return packetsReceived.get();
    }

    public static long errors() {
        return errors.get();
    }

    public static void reset() {
        packetsSent.set(0);
        packetsReceived.set(0);
        errors.set(0);
        totalEncodeTime.set(0);
        totalDecodeTime.set(0);
        totalHandleTime.set(0);
    }

    public static void log() {
        LOGGER.info("=== Network Diagnostics ===");
        LOGGER.info("Packets sent:     {}", packetsSent());
        LOGGER.info("Packets received: {}", packetsReceived());
        LOGGER.info("Errors:           {}", errors());
        LOGGER.info("===========================");
    }

}
