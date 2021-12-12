package com.mdrsolutions.SpringJmsExample.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
@Slf4j
public class GoodsOrderProcessingMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String text = ((TextMessage) message).getText();
            log.info(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
