package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.GoodsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class GoodsOrderService {

    private static final String GOODS_QUEUE = "goods.order.queue";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public void send(GoodsOrder goodsOrder, String storeId, String orderState) {
        jmsTemplate.convertAndSend(GOODS_QUEUE, goodsOrder, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("goodsOrderId", goodsOrder.getGoodsOrderId());
                message.setStringProperty("storeId", storeId);
                message.setStringProperty("orderState", orderState);
                return message;
            }
        });
    }
}
