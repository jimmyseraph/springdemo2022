package vip.testops.order.entities.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsDTO {
    private long id;
    private String name;
    private BigDecimal price;
}
