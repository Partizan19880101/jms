package com.mdrsolutions.SpringJmsExample.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Goods {

    @JsonCreator
    public Goods(@JsonProperty("goodsId") String goodsId, @JsonProperty("title") String title, @JsonProperty("quantity") long quantity) {
        this.goodsId = goodsId;
        this.title = title;
        this.quantity = quantity;
    }

    private final String goodsId;
    private final String title;
    private final long quantity;
}
