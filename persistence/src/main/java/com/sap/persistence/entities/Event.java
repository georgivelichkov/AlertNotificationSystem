package com.sap.persistence.entities;

import com.sap.persistence.converters.EventCategoryConverter;
import com.sap.persistence.converters.EventSeverityConverter;
import com.sap.persistence.enums.EventCategory;
import com.sap.persistence.enums.EventSeverity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Objects;

@Document(collection = "events")
public class Event {

    @Id
    @Column(name = "id")
    private final ObjectId _id;

    @Column(name = "type")
    private final String type;

    @Column(name = "subject")
    private final String subject;

    @Column(name = "body")
    private final String body;


    @Column(name = "severity")
    @Convert(converter = EventSeverityConverter.class)
    private final EventSeverity severity;


    @Column(name = "category")
    @Convert(converter = EventCategoryConverter.class)
    private final EventCategory category;

    public Event() {
        this(null, null, null, null, null);
    }

    public Event(String type, String subject, String body, EventSeverity severity, EventCategory category) {
        this(null, type, subject, body, severity, category);
    }

    public Event(ObjectId id, String type, String subject, String body, EventSeverity severity, EventCategory category) {
        this._id = id;
        this.type = type;
        this.subject = subject;
        this.body = body;
        this.severity = severity;
        this.category = category;
    }

    public String getId() { return _id.toHexString(); }

    public String getType() {
        return type;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public EventSeverity getSeverity() {
        return severity;
    }

    public EventCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Event)) {
            return false;
        }

        Event event = (Event) obj;
        return event.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subject, body, severity, category);
    }
}

