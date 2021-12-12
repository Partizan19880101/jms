package com.mdrsolutions.SpringJmsExample.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class ProcessedGoodsOrder {

    @JsonCreator
    public ProcessedGoodsOrder(
            @JsonProperty("goodsOrder") GoodsOrder goodsOrder,
            @JsonProperty("processingDateTime") Date processingDateTime,
            @JsonProperty("expectedShippingDateTime") Date expectedShippingDateTime) {
        this.goodsOrder = goodsOrder;
        this.processingDateTime = processingDateTime;
        this.expectedShippingDateTime = expectedShippingDateTime;
    }

    private final GoodsOrder goodsOrder;
    private final Date processingDateTime;
    private final Date expectedShippingDateTime;
}
