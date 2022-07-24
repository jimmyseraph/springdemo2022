package vip.testops.order.services;

import vip.testops.order.entities.dto.OrderDTO;
import vip.testops.order.entities.req.GoodsInfo;
import vip.testops.order.entities.resp.OrderDetailResp;
import vip.testops.order.entities.resp.Response;

import java.util.List;

public interface OrderService {
    void doCreate(long userId, List<GoodsInfo> goodsInfoList, Response<?> response);
    void doList(long userId, Response<List<OrderDTO>> response);
    void doDetail(long id, long userId, String username, Response<OrderDetailResp> response);
}
