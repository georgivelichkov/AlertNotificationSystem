package com.sap.persistence.enums;


public enum PredicateType {
    EQUALS("equals"),
    CONTAINS("contains");

    private final String key;

    PredicateType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static PredicateType fromKey(String key) {
        switch (key) {

            case "equals":
                return  PredicateType.EQUALS;

            case "contains":
                return PredicateType.CONTAINS;

                default:
                    throw new IllegalArgumentException("Key [" + key + "] not supported.");

        }
    }
}
