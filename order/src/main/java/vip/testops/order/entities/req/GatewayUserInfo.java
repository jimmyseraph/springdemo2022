package vip.testops.order.entities.req;

import lombok.Data;

@Data
public class GatewayUserInfo {
    private long userId;
    private String username;
    private String email;
}
