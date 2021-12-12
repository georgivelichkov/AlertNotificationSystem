package com.sap.processor.listeners;

import com.sap.messagebroker.MessageBrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


public class EmailQueueListener {

    private static final Logger LOG = LoggerFactory.getLogger(EmailQueueListener.class);
    private MessageBrokerService messageBroker;

    @RabbitListener(queues = "${jsa.rabbitmq.email_queue}")
    public void onMessageReceived(Message message) {
        LOG.info("Received email queue message as generic: {}", message.toString());
        LOG.info("Sending message to email!!");
        messageBroker.sendSimpleMessage("gjvelichkov@gmail.com", new String(message.getBody()));
    }

    @Autowired
    public void setMessageBroker(MessageBrokerService messageBroker) {
        this.messageBroker = messageBroker;
    }
}
