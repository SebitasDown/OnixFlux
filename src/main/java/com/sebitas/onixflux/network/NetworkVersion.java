package com.sebitas.onixflux.network;

public final class NetworkVersion {

    public static final int MAJOR = 1;
    public static final int MINOR = 0;
    public static final int PATCH = 0;

    private static final String VERSION = MAJOR + "." + MINOR + "." + PATCH;

    private NetworkVersion() {}

    public static String version() {
        return VERSION;
    }

    public static boolean isCompatible(String other) {
        if (other == null) return false;
        String[] parts = other.split("\\.");
        try {
            int otherMajor = Integer.parseInt(parts[0]);
            return otherMajor == MAJOR;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

}
