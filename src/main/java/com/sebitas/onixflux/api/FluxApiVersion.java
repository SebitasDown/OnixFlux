package com.sebitas.onixflux.api;

public final class FluxApiVersion {

    public static final int MAJOR = 1;
    public static final int MINOR = 0;
    public static final int PATCH = 0;

    private static final String VERSION = MAJOR + "." + MINOR + "." + PATCH;

    private FluxApiVersion() {}

    public static String version() {
        return VERSION;
    }

    public static int major() {
        return MAJOR;
    }

    public static int minor() {
        return MINOR;
    }

    public static int patch() {
        return PATCH;
    }

    public static boolean isCompatible(String otherVersion) {
        String[] parts = otherVersion.split("\\.");
        try {
            int otherMajor = Integer.parseInt(parts[0]);
            return otherMajor == MAJOR;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

}
