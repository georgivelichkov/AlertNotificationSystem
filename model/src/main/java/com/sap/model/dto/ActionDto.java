package com.sap.model.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.persistence.enums.ActionType;

import java.io.Serializable;
import java.util.UUID;

public class ActionDto implements Serializable {

    private final UUID id;
    private final String name;
    private final ActionType type;
    private final String description;
    private final String attribute;

    public ActionDto(UUID id, String name, ActionType type, String description, String attribute) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.attribute = attribute;
    }

    @JsonCreator
    public ActionDto(@JsonProperty("name") String name, @JsonProperty("type") ActionType type,
                     @JsonProperty("description") String description, @JsonProperty("attribute") String attribute) {
        this(null, name, type, description, attribute);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ActionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getAttribute() {
        return attribute;
    }
}
