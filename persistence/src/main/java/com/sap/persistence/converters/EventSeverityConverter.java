package com.sap.persistence.converters;

import com.sap.persistence.enums.EventSeverity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EventSeverityConverter implements AttributeConverter<EventSeverity, String> {

    @Override
    public String convertToDatabaseColumn(EventSeverity severity) {
        return severity.getKey();
    }

    @Override
    public EventSeverity convertToEntityAttribute(String key) {
        return EventSeverity.fromKey(key);
    }
}
