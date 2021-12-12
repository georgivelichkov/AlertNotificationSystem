package com.sap.persistence.enums;

public enum EventSeverity {
    INFO("Info"),
    WARNING("Warning"),
    ERROR("Error");

    private final String key;

    EventSeverity(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static EventSeverity fromKey( String key) {
        switch (key) {

            case "Info":
                return EventSeverity.INFO;

            case "Warning":
                return EventSeverity.WARNING;

            case "Error":
                return EventSeverity.ERROR;

            default:
                throw new IllegalArgumentException("Key [" + key + "] not supported.");

        }
    }
}
