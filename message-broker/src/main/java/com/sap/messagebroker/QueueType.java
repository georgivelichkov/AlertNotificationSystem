package com.sap.messagebroker;

public enum QueueType {
    EMAIL("EMAIL","customer.email"),
    SLACK("SLACK","customer.slack"),
    STORE("STORE","customer.db"),
    EVENT("Event","customer.events");

    private String type;
    private String routingKey;

    QueueType(String type, String routingKey) {
        this.type = type;
        this.routingKey = routingKey;
    }

    public String getType() {
        return type;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public static QueueType fromKey(String key) {
        return QueueType.valueOf(key);
    }
}
