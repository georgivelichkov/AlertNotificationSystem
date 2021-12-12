package com.sap.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.persistence.enums.EventCategory;
import com.sap.persistence.enums.EventSeverity;

import java.io.Serializable;
import java.util.UUID;

public class EventDto implements Serializable {

    private final UUID id;
    private final String type;
    private final String subject;
    private final String body;
    private final EventSeverity severity;
    private final EventCategory category;

    public EventDto() {
        this(null, null, null ,null, null, null);
    }

    public EventDto(UUID id, String type, String subject, String body, EventSeverity severity, EventCategory category) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.body = body;
        this.severity = severity;
        this.category = category;
    }

    @JsonCreator
    public EventDto(@JsonProperty("type") String type, @JsonProperty("subject") String subject, //
                    @JsonProperty("body") String body, @JsonProperty("severity") EventSeverity severity, //
                    @JsonProperty("category") EventCategory category ) {
        this(null, type, subject, body, severity, category);
    }

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

}
