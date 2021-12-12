package com.sap.persistence.converters;

import com.sap.persistence.enums.EventCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EventCategoryConverter implements AttributeConverter<EventCategory, String> {

    @Override
    public String convertToDatabaseColumn(EventCategory category) {
        return category.getKey();
    }

    @Override
    public EventCategory convertToEntityAttribute(String key) {
        return EventCategory.fromKey(key);
    }
}
