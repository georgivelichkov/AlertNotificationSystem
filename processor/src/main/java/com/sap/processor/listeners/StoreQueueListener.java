package com.sap.processor.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.model.DtoConverter;
import com.sap.model.dto.EventDto;
import com.sap.model.services.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class StoreQueueListener {

    private static final Logger LOG = LoggerFactory.getLogger(EmailQueueListener.class);
    private EventsService service;

    @RabbitListener(queues = "${jsa.rabbitmq.db_queue}")
    public void onMessageReceived(Message message) throws IOException {
        LOG.info("Received message from store_queue as generic: {}", message.toString());
        LOG.info("Sending message to service for storing!!");
        EventDto dto = mapFromJson(new String(message.getBody()), EventDto.class);
        service.save(DtoConverter.toEvent(dto));
    }

    @Autowired
    public void setService(EventsService service) {
        this.service = service;
    }

    private <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
