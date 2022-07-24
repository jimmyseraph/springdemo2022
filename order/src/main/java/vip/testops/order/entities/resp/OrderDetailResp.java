package vip.testops.order.entities.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailResp {
    private long id;
    @JsonProperty("custom_id")
    private long customId;
    private String custom;
    private List<GoodsOrder> list;
}
