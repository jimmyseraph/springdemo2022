package vip.testops.order.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.testops.order.entities.dto.OrderDTO;
import vip.testops.order.entities.req.CreateOrderReq;
import vip.testops.order.entities.req.GatewayUserInfo;
import vip.testops.order.entities.resp.OrderDetailResp;
import vip.testops.order.entities.resp.Response;
import vip.testops.order.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
@Tag(name="order interface")
public class OrderController {

    private final String USER_ID_IN_HEADER = "user-id";
    private final String USER_NAME_IN_HEADER = "user-name";
    private final String USER_EMAIL_IN_HEADER = "user-email";

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    // create order
    @PostMapping("/new")
    public Response<?> createOrder(@RequestBody CreateOrderReq req, HttpServletRequest servletRequest) {
        Response<?> response = new Response<>();
        // 1 check req params
        if (req.getGoodsList() == null || req.getGoodsList().size() == 0){
            response.missingParam("goods_list");
            return response;
        }
        // 2. get userID from header
        GatewayUserInfo userInfo = getUserInfo(servletRequest);
        if(userInfo == null) {
            response.missingParam("user-id");
            return response;
        }
        long userId = userInfo.getUserId();
        // deep into service
        orderService.doCreate(userId, req.getGoodsList(), response);
        return response;
    }
    // order list
    @GetMapping("/list")
    public Response<List<OrderDTO>> list(HttpServletRequest servletRequest) {
        Response<List<OrderDTO>> response = new Response<>();
        // 1. get user info from header
        GatewayUserInfo userInfo = getUserInfo(servletRequest);
        if(userInfo == null) {
            response.missingParam("user-id");
            return response;
        }
        long userId = userInfo.getUserId();
        // 2. deep into service
        orderService.doList(userId, response);
        return response;
    }
    // order detail
    @GetMapping("/{id}/detail")
    public Response<OrderDetailResp> detail(@PathVariable long id, HttpServletRequest servletRequest) {
        Response<OrderDetailResp> response = new Response<>();
        // 1. get user info from header
        GatewayUserInfo userInfo = getUserInfo(servletRequest);
        if(userInfo == null) {
            response.missingParam("user-id/user-name");
            return response;
        }
        // 2. deep into service
        orderService.doDetail(id, userInfo.getUserId(), userInfo.getUsername(), response);
        return response;
    }

    private GatewayUserInfo getUserInfo(HttpServletRequest servletRequest){
        String userIdHeader = servletRequest.getHeader(USER_ID_IN_HEADER);
        String userNameHeader = servletRequest.getHeader(USER_NAME_IN_HEADER);
        String userEmailHeader = servletRequest.getHeader(USER_EMAIL_IN_HEADER);
        if (userIdHeader == null || userIdHeader.equals("")
                || userNameHeader == null || userNameHeader.equals("")
                || userEmailHeader == null || userEmailHeader.equals("")
        ){
            return null;
        }
        GatewayUserInfo userInfo = new GatewayUserInfo();
        userInfo.setUserId(Long.parseLong(userIdHeader));
        userInfo.setUsername(userNameHeader);
        userInfo.setEmail(userEmailHeader);
        return userInfo;
    }
}
