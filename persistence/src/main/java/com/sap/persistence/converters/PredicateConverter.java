package com.sap.persistence.converters;

import com.sap.persistence.enums.PredicateType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PredicateConverter implements AttributeConverter<PredicateType, String> {

    @Override
    public String convertToDatabaseColumn(PredicateType type) {
        return type.getKey();
    }

    @Override
    public PredicateType convertToEntityAttribute(String key) {
        return PredicateType.fromKey(key);
    }
}
