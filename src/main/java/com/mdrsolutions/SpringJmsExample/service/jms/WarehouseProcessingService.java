package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.GoodsOrder;
import com.mdrsolutions.SpringJmsExample.pojos.ProcessedGoodsOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class WarehouseProcessingService {
    private static final String PROCESSED_QUEUE = "goods.order.processed.queue";
    private static final String CANCELED_QUEUE = "goods.order.canceled.queue";

    @Transactional
    public JmsResponse<Message<ProcessedGoodsOrder>> processOrder(GoodsOrder goodsOrder, String orderState, String storeId) {

        Message<ProcessedGoodsOrder> message;

        if ("NEW".equalsIgnoreCase(orderState)) {
            message = add(goodsOrder, storeId);
            return JmsResponse.forQueue(message, PROCESSED_QUEUE);
        } else if ("UPDATE".equalsIgnoreCase(orderState)) {
            message = update(goodsOrder, storeId);
            return JmsResponse.forQueue(message, PROCESSED_QUEUE);
        } else if ("DELETE".equalsIgnoreCase(orderState)) {
            message = delete(goodsOrder, storeId);
            return JmsResponse.forQueue(message, CANCELED_QUEUE);
        } else {
            throw new IllegalArgumentException("WarehouseProcessingService.processOrder(...) - orderState does not match expected criteria!");
        }

    }

    private Message<ProcessedGoodsOrder> add(GoodsOrder goodsOrder, String storeId) {
        log.info("ADDING A NEW ORDER TO DB");
        return build(new ProcessedGoodsOrder(
                goodsOrder,
                new Date(),
                new Date()
        ), "ADDED", storeId);
    }

    private Message<ProcessedGoodsOrder> update(GoodsOrder goodsOrder, String storeId) {
        log.info("UPDATING A ORDER TO DB");
        return build(new ProcessedGoodsOrder(
                goodsOrder,
                new Date(),
                new Date()
        ), "UPDATED", storeId);
    }

    private Message<ProcessedGoodsOrder> delete(GoodsOrder goodsOrder, String storeId) {
        log.info("DELETING ORDER FROM DB");
        return build(new ProcessedGoodsOrder(
                goodsOrder,
                new Date(),
                null
        ), "DELETED", storeId);
    }

    private Message<ProcessedGoodsOrder> build(ProcessedGoodsOrder goodsOrder, String orderState, String storeId) {
        return MessageBuilder
                .withPayload(goodsOrder)
                .setHeader("orderState", orderState)
                .setHeader("storeId", storeId)
                .build();
    }
}
