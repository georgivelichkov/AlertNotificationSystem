package com.sap.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.persistence.enums.PredicateType;

import java.io.Serializable;
import java.util.UUID;

public class ConditionDto implements Serializable {

    private final UUID id;
    private final String name;
    private final PredicateType predicate;
    private final String value;

    public ConditionDto() {
        this(null, null, null, null);
    }

    @JsonCreator
    public ConditionDto(@JsonProperty("name") String name, @JsonProperty("predicate") PredicateType predicate, @JsonProperty("value") String value) {
        this(null, name, predicate, value);
    }

    public ConditionDto(UUID id, String name, PredicateType predicate, String value) {
        this.id = id;
        this.name = name;
        this.predicate = predicate;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PredicateType getPredicate() {
        return predicate;
    }

    public String getValue() {
        return value;
    }


}
