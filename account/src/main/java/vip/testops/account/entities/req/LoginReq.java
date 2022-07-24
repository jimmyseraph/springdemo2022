package vip.testops.account.entities.req;

import lombok.Data;

@Data
public class LoginReq {
    private String name;
    private String password;
}
