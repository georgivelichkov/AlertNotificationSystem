package com.sap.persistence.converters;

import com.sap.persistence.enums.ActionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActionTypeConverter implements AttributeConverter<ActionType, String> {

    @Override
    public String convertToDatabaseColumn(ActionType actionType) {
        return actionType.getKey();
    }

    @Override
    public ActionType convertToEntityAttribute(String key) {
        return ActionType.fromKey(key);
    }
}
