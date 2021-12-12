package com.sap.persistence.entities;

import com.sap.persistence.converters.ActionTypeConverter;
import com.sap.persistence.enums.ActionType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, nullable = false)
    private final  UUID id;

    @Column(name = "name")
    private final String name;

    @Column(name = "type")
    @Convert(converter = ActionTypeConverter.class)
    private final  ActionType type;

    @Column(name = "description")
    private final  String description;

    @Column(name = "attribute")
    private final  String attribute;

    public Action() {
        // EMPTY CONSTRUCTOR FOR JPA
        this(null, null, null, null, null);
    }

    public Action(String name, ActionType actionType, String description, String attribute) {
        this(null, name, actionType, description, attribute);
    }

    public Action(UUID id, String name, ActionType actionType, String description, String attribute) {
        this.id = id;
        this.name = name;
        this.type = actionType;
        this.description = description;
        this.attribute = attribute;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Action)) {
            return false;
        }


        Action action = (Action) obj;


        return  action.getId().equals(getId()) && action.getType().equals(getType()) &&
                action.getName().equals(getName()) && action.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, description, attribute);
    }
}
