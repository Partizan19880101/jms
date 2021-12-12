package com.mdrsolutions.SpringJmsExample.controller;

import com.mdrsolutions.SpringJmsExample.pojos.Customer;
import com.mdrsolutions.SpringJmsExample.pojos.Goods;
import com.mdrsolutions.SpringJmsExample.pojos.GoodsOrder;
import com.mdrsolutions.SpringJmsExample.service.jms.GoodsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class AppController {

    @Autowired
    private GoodsOrderService goodsOrderService;

    List<Goods> goods = Arrays.asList(
            new Goods("test1", "Water", 50000L),
            new Goods("test2", "Coke", 0),
            new Goods("test3", "Energy Drink", 0));


    List<Customer> customers = Arrays.asList(
            new Customer("test1", "Romulus"),
            new Customer("test2", "Jack"),
            new Customer("test3", "John")
    );

    @RequestMapping("/")
    public String appHome(ModelMap map) {
        map.addAttribute("customers", customers);
        map.addAttribute("goods", goods);
        return "index";
    }

    @RequestMapping(path = "/process/store/{storeId}/order/{orderId}/{customerId}/{goodsId}/{orderState}/", method = RequestMethod.GET)
    public @ResponseBody
    String processOrder(@PathVariable("storeId") String storeId,
                        @PathVariable("orderId") String orderId,
                        @PathVariable("customerId") String customerId,
                        @PathVariable("goodsId") String goodsId,
                        @PathVariable("orderState") String orderState) {

        try {
            log.info("StoredId is = {}", storeId);
            goodsOrderService.send(build(customerId, goodsId, orderId), storeId, orderState);
        } catch (Exception exception) {
            return "Error occurred!" + exception.getLocalizedMessage();
        }
        return "Order sent to warehouse for goodsId = " + goodsId + " from customerId = " + customerId + " successfully processed!";
    }

    private GoodsOrder build(String customerId, String goodsId, String orderId) {
        Goods myGoods = null;
        Customer myCustomer = null;

        for (Goods bk : goods) {
            if (bk.getGoodsId().equalsIgnoreCase(goodsId)) {
                myGoods = bk;
            }
        }
        if (null == myGoods) {
            throw new IllegalArgumentException("Goods selected does not exist in inventory!");
        }

        for (Customer ct : customers) {
            if (ct.getCustomerId().equalsIgnoreCase(customerId)) {
                myCustomer = ct;
            }
        }

        if (null == myCustomer) {
            throw new IllegalArgumentException("Customer selected does not appear to be valid!");
        }

        return new GoodsOrder(orderId, myGoods, myCustomer);
    }


}
