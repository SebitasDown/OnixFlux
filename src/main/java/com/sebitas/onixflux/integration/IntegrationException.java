package com.sebitas.onixflux.integration;

public class IntegrationException extends RuntimeException {

    private final String integrationId;
    private final Type type;

    public enum Type {
        NOT_FOUND,
        LOAD_FAILED,
        VERSION_MISMATCH,
        DEPENDENCY_MISSING,
        CONFIG_ERROR,
        DISABLED,
        CONFLICT
    }

    public IntegrationException(String integrationId, Type type, String message) {
        super(message);
        this.integrationId = integrationId;
        this.type = type;
    }

    public IntegrationException(String integrationId, Type type, String message, Throwable cause) {
        super(message, cause);
        this.integrationId = integrationId;
        this.type = type;
    }

    public String integrationId() {
        return integrationId;
    }

    public Type type() {
        return type;
    }

}
