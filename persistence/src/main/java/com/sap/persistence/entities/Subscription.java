package com.sap.persistence.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, nullable = false)
    private final UUID id;

    @Column(name = "name")
    private final String name;

    @ManyToMany
    @JoinTable(
            name = "subscriptions_conditions",
            joinColumns = @JoinColumn(name = "subscription_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id", referencedColumnName="id")
    )
    private final List<Condition> conditions;

    @ManyToMany
    @JoinTable(
            name = "subscriptions_actions",
            joinColumns = @JoinColumn(name = "subscription_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "action_id", referencedColumnName="id")
    )
    private final List<Action> actions;

    public Subscription(){
        //FOR JPA
        this(null, null, null, null);
    }

    public Subscription(String name, List<Condition> conditions, List<Action> actions) {
        this(null, name, conditions, actions);
    }

    public Subscription(UUID id, String name, List<Condition> conditions, List<Action> actions) {
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

    public List<Action> getActions() {
        return actions;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Subscription)) {
            return false;
        }

        Subscription sub = (Subscription) obj;
        return sub.getId().equals(getId()) && sub.getConditions().equals(getConditions()) && sub.getActions().equals(getActions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, actions, conditions);
    }
}
