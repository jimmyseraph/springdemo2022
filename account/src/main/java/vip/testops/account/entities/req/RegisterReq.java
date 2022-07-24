package vip.testops.account.entities.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(title = "user register parameters")
@Data
public class RegisterReq {
    @Schema(title = "user name", required = true, example = "liudao001")
    private String name;
    @Schema(title = "user email", required = true, example = "liudao001@testops.vip")
    private String email;
    @Schema(title = "password", required = true, example = "123456")
    private String password;
}
