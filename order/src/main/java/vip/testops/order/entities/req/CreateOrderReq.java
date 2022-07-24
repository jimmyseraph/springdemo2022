package vip.testops.order.entities.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderReq {
    @JsonProperty("goods_list")
    private List<GoodsInfo> goodsList;
}
