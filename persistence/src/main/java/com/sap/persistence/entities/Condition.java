package com.sap.persistence.entities;

import com.sap.persistence.converters.PredicateConverter;
import com.sap.persistence.enums.PredicateType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "conditions")
public class Condition {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, nullable = false)
    private final UUID id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private final String name;

    @Column(name = "predicate", columnDefinition = "VARCHAR(255)")
    @Convert(converter = PredicateConverter.class)
    private final PredicateType predicate;

    @Column(name = "value", columnDefinition = "VARCHAR(255)")
    private final String value;

    public Condition() {
        this(null, null, null, null);
    }

    public Condition(String name, PredicateType predicate, String value) {
        this(null, name, predicate, value);
    }

    public Condition(UUID id, String name, PredicateType predicate, String value) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Condition)) {
            return false;
        }


        Condition condition = (Condition) obj;


        return  condition.getId().equals(getId()) && condition.getPredicate().equals(getPredicate()) && condition.getValue().equals(getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, predicate, value);
    }
}
