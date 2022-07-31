package vip.testops.order.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.testops.order.clients.GoodsClient;
import vip.testops.order.entities.dto.GoodsDTO;
import vip.testops.order.entities.dto.OrderDTO;
import vip.testops.order.entities.dto.OrderDetailDTO;
import vip.testops.order.entities.req.GoodsInfo;
import vip.testops.order.entities.resp.GoodsOrder;
import vip.testops.order.entities.resp.OrderDetailResp;
import vip.testops.order.entities.resp.Response;
import vip.testops.order.mappers.OrderDetailMapper;
import vip.testops.order.mappers.OrderMapper;
import vip.testops.order.services.OrderService;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;
    private GoodsClient goodsClient;

    @Autowired
    public void setGoodsClient(GoodsClient goodsClient) {
        this.goodsClient = goodsClient;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
    @Autowired
    public void setOrderDetailMapper(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doCreate(long userId, List<GoodsInfo> goodsInfoList, Response<?> response) throws Exception{
        // 1 create orderDTO, and insert into db
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomId(userId);
        orderMapper.addOrder(orderDTO);

        // insert goodsInfoList to db
        for(GoodsInfo goodsInfo : goodsInfoList) {
            Response<GoodsDTO> goodsDetailResp = goodsClient.getGoods(goodsInfo.getGoodsId());
            if(goodsDetailResp.getCode() != 1000) {
                response.setCode(goodsDetailResp.getCode());
                response.setMessage(goodsDetailResp.getMessage());
                throw new RuntimeException("internal error.");
            }
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setOrderId(orderDTO.getId());
            orderDetailDTO.setGoodsId(goodsInfo.getGoodsId());
            orderDetailDTO.setAmount(goodsInfo.getAmount());
            orderDetailDTO.setPrice(goodsDetailResp.getData().getPrice());
            orderDetailMapper.addOrderDetail(orderDetailDTO);
        }

        response.commonSuccess();
    }

    @Override
    public void doList(long userId, Response<List<OrderDTO>> response) {
        List<OrderDTO> orderDTOList = orderMapper.getOrderListByCustomId(userId);
        response.successWithData(orderDTOList);
    }

    @Override
    public void doDetail(long id, long userId, String username, Response<OrderDetailResp> response) throws Exception{
        List<GoodsOrder> list = new ArrayList<>();
        List<OrderDetailDTO> orderDetailDTOList = orderDetailMapper.getDetailByOrderId(id);
        for(OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
            Response<GoodsDTO> goodsDetailResp = goodsClient.getGoods(orderDetailDTO.getGoodsId());
            if(goodsDetailResp.getCode() != 1000) {
                response.setCode(goodsDetailResp.getCode());
                response.setMessage(goodsDetailResp.getMessage());
                throw new RuntimeException("internal error.");
            }
            GoodsOrder goodsOrder = new GoodsOrder();
            goodsOrder.setGoodsId(orderDetailDTO.getGoodsId());
            goodsOrder.setGoodsName(goodsDetailResp.getData().getName());
            goodsOrder.setPrice(orderDetailDTO.getPrice());
            goodsOrder.setAmount(orderDetailDTO.getAmount());
            list.add(goodsOrder);
        }

        OrderDetailResp orderDetailResp = new OrderDetailResp();
        orderDetailResp.setCustomId(userId);
        orderDetailResp.setCustom(username);
        orderDetailResp.setId(id);
        orderDetailResp.setList(list);
        response.successWithData(orderDetailResp);
    }
}
