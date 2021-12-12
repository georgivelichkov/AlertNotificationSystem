package com.sap.persistence.enums;

public enum ActionType {

    EMAIL("EMAIL"),
    SLACK("SLACK"),
    STORE("STORE");

    private final String key;

    ActionType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static ActionType fromKey(String key) {
        switch (key) {

            case "EMAIL":
                return ActionType.EMAIL;

            case "SLACK":
                return ActionType.SLACK;

            case "STORE":
                return ActionType.STORE;

            default:
                throw new IllegalArgumentException("Key [" + key + "] not supported.");

        }
    }
}
