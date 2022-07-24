package vip.testops.order.entities.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private long id;
    private long customId;
    private Date createTime;
}
