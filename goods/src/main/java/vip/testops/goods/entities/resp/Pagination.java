package vip.testops.goods.entities.resp;

import lombok.Data;

@Data
public class Pagination {
    private int pageSize;
    private int current;
    private int total;
}
