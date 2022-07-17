package vip.testops.account.entities.resp;

import lombok.Data;

@Data
public class RegisterResp {
    private Long id;
    private String name;
    private String email;
    private String token;
}
