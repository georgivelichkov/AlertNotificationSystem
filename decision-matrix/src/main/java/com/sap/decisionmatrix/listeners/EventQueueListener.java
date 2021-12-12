package com.sap.decisionmatrix.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.messagebroker.MessageBrokerService;
import com.sap.messagebroker.QueueType;
import com.sap.model.dto.EventDto;
import com.sap.model.services.SubscriptionsService;
import com.sap.persistence.entities.Action;
import com.sap.persistence.entities.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;


public class EventQueueListener {

    private static final Logger LOG = LoggerFactory.getLogger(EventQueueListener.class);
    private SubscriptionsService service;
    private MessageBrokerService brokerService;

    @Autowired
    public EventQueueListener(SubscriptionsService service, MessageBrokerService brokerService) {
        this.service = service;
        this.brokerService = brokerService;
    }

    @RabbitListener(queues = "${jsa.rabbitmq.event_queue}")
    public void onMessageReceived(Message message) {
        String json = new String(message.getBody());
        LOG.info("Received message from event queue as generic: {}", json);
        try {
            processMessage(new String(message.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(String message) throws IOException {
        //EventDto dto = new ObjectMapper().readValue(message, EventDto.class);
        System.out.println(message);
        EventDto dto = mapFromJson(message, EventDto.class);
        System.out.println(dto.getType());
        /*Subscription sub = service.getByName(dto.getType());

        validateEvent(dto,sub.getConditions());
        sub.getActions().forEach(action -> {
            try {
                sendToQueue(dto,action);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });*/
    }

    private void validateEvent(EventDto request, List<Condition> conditions) {
        conditions.forEach(condition -> checkCondition(condition,request));
    }

    private void checkCondition(Condition condition, EventDto request) {
        switch (condition.getPredicate().getKey()) {
            case "equals":
                if(!request.getBody().equals(condition.getValue()))
                    throw new IllegalArgumentException("Event does not meet conditions.");
                break;
            case "contains":
                if(!request.getBody().contains(condition.getValue()))
                    throw new IllegalArgumentException("Event does not meet conditions.");
                break;

            default:
                throw new IllegalArgumentException("Event does not meet conditions.");

        }
    }

    private void sendToQueue(EventDto request, Action action) throws JsonProcessingException {
        QueueType queueType = QueueType.fromKey(action.getType().getKey());
        brokerService.sendEventToQueue(request,queueType);
    }

    private <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return objectMapper.readValue(json, clazz);
    }
}
