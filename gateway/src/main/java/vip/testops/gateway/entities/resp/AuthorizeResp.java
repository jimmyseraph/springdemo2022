package vip.testops.gateway.entities.resp;

import lombok.Data;

@Data
public class AuthorizeResp {
    private long id;
    private String name;
    private String email;
}
