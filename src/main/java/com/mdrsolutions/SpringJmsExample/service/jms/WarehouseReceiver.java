package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.GoodsOrder;
import com.mdrsolutions.SpringJmsExample.pojos.ProcessedGoodsOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WarehouseReceiver {

    @Autowired
    private WarehouseProcessingService warehouseProcessingService;

    @JmsListener(destination = "goods.order.queue")
    public JmsResponse<Message<ProcessedGoodsOrder>> receive(@Payload GoodsOrder goodsOrder,
                                                             @Header(name = "orderState") String orderState,
                                                             @Header(name = "goodsOrderId") String goodsOrderId,
                                                             @Header(name = "storeId") String storeId
    ) {
        log.info("Message received!");
        log.info("Message is == " + goodsOrder);
        log.info("Message property orderState = {}, goodsOrderId = {}, storeId = {}", orderState, goodsOrder, storeId);


        if (goodsOrder.getGoods().getQuantity() > 5000L) {
            throw new IllegalArgumentException("Error in order=" + goodsOrder.getGoodsOrderId()
                    + " quantity is unacceptable. Aborting order!");
        }

        return warehouseProcessingService.processOrder(goodsOrder, orderState, storeId);
    }
}
