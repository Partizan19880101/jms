package com.mdrsolutions.SpringJmsExample.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GoodsOrder {

    @JsonCreator
    public GoodsOrder(@JsonProperty("goodsOrderId") String goodsOrderId, @JsonProperty("goods") Goods goods, @JsonProperty("customer") Customer customer) {
        this.goodsOrderId = goodsOrderId;
        this.goods = goods;
        this.customer = customer;
    }

    private final String goodsOrderId;
    private final Goods goods;
    private final Customer customer;
}

