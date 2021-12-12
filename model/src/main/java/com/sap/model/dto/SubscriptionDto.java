package com.sap.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sap.persistence.entities.Action;
import com.sap.persistence.entities.Condition;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class SubscriptionDto implements Serializable {

    private final UUID id;
    private final String name;
    private final List<Condition> conditions;
    private final List<Action> actions;

    public SubscriptionDto() {
        this(null, null, null, null);
    }

    @JsonCreator
    public SubscriptionDto(String name, List<Condition> conditions, List<Action> actions) {
        this(null, name, conditions, actions);
    }

    public SubscriptionDto(UUID id, String name, List<Condition> conditions, List<Action> actions) {
        this.id = id;
        this.name = name;
        this.conditions = conditions;
        this.actions = actions;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Action> getActions() {
        return actions;
    }

}
