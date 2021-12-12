package com.sap.persistence.enums;

public enum  EventCategory {
    ALERT("Alert"),
    NOTIFICATION("Notification");

    private final String key;

    EventCategory(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static EventCategory fromKey(String key) {
        switch (key) {

            case "Alert":
                return EventCategory.ALERT;

            case "Notification":
                return EventCategory.NOTIFICATION;

            default:
                throw new IllegalArgumentException("Key [" + key + "] not supported.");

        }
    }
}
