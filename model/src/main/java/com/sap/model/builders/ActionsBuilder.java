package com.sap.model.builders;

import com.sap.persistence.entities.Action;
import com.sap.persistence.enums.ActionType;

public class ActionsBuilder {

    private String name = "Action A";
    private ActionType type = ActionType.SLACK;
    private String description = "Action A has some description";
    private String attribute = "example@example.com";


    public ActionsBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ActionsBuilder withType(ActionType type) {
        this.type = type;
        return this;
    }

    public ActionsBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ActionsBuilder withAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public Action build() {
        return new Action(name, type, description, attribute);
    }


}
