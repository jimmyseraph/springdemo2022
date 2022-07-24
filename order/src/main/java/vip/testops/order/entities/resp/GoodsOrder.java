package vip.testops.order.entities.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsOrder {
    @JsonProperty("goods_id")
    private long goodsId;
    @JsonProperty("goods_name")
    private String goodsName;
    private BigDecimal price;
    private int amount;
}
