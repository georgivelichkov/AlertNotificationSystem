package com.sap.model.builders;

import com.sap.persistence.entities.Action;
import com.sap.persistence.entities.Condition;
import com.sap.persistence.entities.Subscription;

import java.util.List;

public class SubscriptionsBuilder {

    private String name = "Subscription A";
    private List<Condition> conditions;
    private List<Action> actions;


    public SubscriptionsBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public SubscriptionsBuilder withConditions( List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public SubscriptionsBuilder withActions( List<Action> actions) {
        this.actions = actions;
        return this;
    }


    public Subscription build() {
        return new Subscription(name, conditions, actions);
    }

}
