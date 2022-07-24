package vip.testops.order.entities.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    private long id;
    private long goodsId;
    private long orderId;
    private BigDecimal price;
    private int amount;
}
