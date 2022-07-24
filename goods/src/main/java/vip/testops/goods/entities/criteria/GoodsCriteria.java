package vip.testops.goods.entities.criteria;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsCriteria {
    private String name;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Integer pageSize;
    private Integer current;
}
