package com.sap.model;

import com.sap.model.dto.ActionDto;
import com.sap.model.dto.ConditionDto;
import com.sap.model.dto.EventDto;
import com.sap.model.dto.SubscriptionDto;
import com.sap.persistence.entities.Action;
import com.sap.persistence.entities.Condition;
import com.sap.persistence.entities.Event;
import com.sap.persistence.entities.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtoConverter {

    private static final Logger log = LoggerFactory.getLogger(DtoConverter.class);

    public static Action toActionWithId(ActionDto request) {
        return new Action(request.getId(),request.getName(), request.getType(), request.getDescription(), request.getAttribute());
    }

    public static Action toAction(ActionDto request) {
        return new Action(request.getName(), request.getType(), request.getDescription(), request.getAttribute());
    }

    public static Condition toConditionWith(ConditionDto dto) {
        return new Condition(dto.getId(), dto.getName(), dto.getPredicate(), dto.getValue());
    }

    public static Condition toCondition(ConditionDto dto) {
        return new Condition(dto.getName(), dto.getPredicate(), dto.getValue());
    }

    public static Subscription toSubscriptionWithId(SubscriptionDto dto) {
        return new Subscription(dto.getId(), dto.getName(), dto.getConditions(), dto.getActions());
    }

    public static Subscription toSubscription(SubscriptionDto dto) {
        return new Subscription(dto.getName(), dto.getConditions(), dto.getActions());
    }

    public static Event toEvent(EventDto dto) {
        return new Event(dto.getType(), dto.getSubject(), dto.getBody(), dto.getSeverity(), dto.getCategory());
    }
}
