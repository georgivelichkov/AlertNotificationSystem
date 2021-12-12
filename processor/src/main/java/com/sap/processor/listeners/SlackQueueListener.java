package com.sap.processor.listeners;

import com.sap.messagebroker.MessageBrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


public class SlackQueueListener {

    private static final Logger LOG = LoggerFactory.getLogger(SlackQueueListener.class);
    private MessageBrokerService messageBroker;

    @RabbitListener(queues = "${jsa.rabbitmq.slack_queue}")
    public void onMessageReceived(Message message) {
        LOG.info("Received slack queue message as generic: {}", message.toString());
        LOG.info("Sending message to slack!!");
        LOG.info("Response from slack: {}", messageBroker.sendMessageToSlack(new String(message.getBody())));
    }

    @Autowired
    public void setMessageBroker(MessageBrokerService messageBroker) {
        this.messageBroker = messageBroker;
    }
}
