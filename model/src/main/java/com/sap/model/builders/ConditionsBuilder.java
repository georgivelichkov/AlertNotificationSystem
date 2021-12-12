package com.sap.model.builders;

import com.sap.persistence.entities.Condition;
import com.sap.persistence.enums.PredicateType;

public class ConditionsBuilder {

    private String name = "Condition A";
    private PredicateType predicate = PredicateType.EQUALS;
    private String value = "Condition A value";

    public ConditionsBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ConditionsBuilder withPredicate(PredicateType predicate) {
        this.predicate = predicate;
        return this;
    }

    public ConditionsBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Condition build() {
        return new Condition(name, predicate, value);
    }
}
