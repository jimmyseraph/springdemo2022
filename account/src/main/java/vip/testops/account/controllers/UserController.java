package vip.testops.account.controllers;

import io.netty.util.internal.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.testops.account.entities.req.LoginReq;
import vip.testops.account.entities.req.RegisterReq;
import vip.testops.account.entities.resp.AuthorizeResp;
import vip.testops.account.entities.resp.RegisterResp;
import vip.testops.account.entities.resp.Response;
import vip.testops.account.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Tag(name="User Interface")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final static String ACCESS_TOKEN = "Access-Token";

    // 注册
    @Operation(summary = "user register", description = "user register")
    @PostMapping("/register")
    public Response<RegisterResp> register(@RequestBody RegisterReq req) {
        Response<RegisterResp> response = new Response<>();
        // 1 检查入参
        if(req.getName() == null || req.getName().equals("")) {
            response.missingParam("name");
            return response;
        }
        if(req.getEmail() == null || req.getEmail().equals("")) {
            response.missingParam("email");
            return response;
        }
        if(req.getPassword() == null || req.getPassword().equals("")) {
            response.missingParam("password");
            return response;
        }
        // 2 进入业务层（service完成实际注册）
        userService.doRegister(req, response);

        return response;
    }

    // 登录
    @Operation(summary = "user login", description = "user login")
    @PostMapping("/login")
    public Response<RegisterResp> login(@RequestBody LoginReq req) {
        Response<RegisterResp> response = new Response<>();
        // 1. 检查入参
        if(req.getName() == null || req.getName().equals("")) {
            response.missingParam("name");
            return response;
        }
        if(req.getPassword() == null || req.getPassword().equals("")) {
            response.missingParam("password");
            return response;
        }
        // 2. 进入业务层
        userService.doLogin(req, response);
        return response;
    }

    // 登出
    @Operation(summary = "user logout", description = "user logout")
    @GetMapping("/logout")
    public Response<?> logout(HttpServletRequest request) {
        Response<?> response = new Response<>();
        // 1. 从header中取出token
        String token = request.getHeader(ACCESS_TOKEN);
        if (StringUtil.isNullOrEmpty(token)) {
            response.invalidToken();
            return response;
        }
        // 2. 校验token，获得用户信息
        Response<AuthorizeResp> resp = new Response<>();
        userService.doAuthorize(token, resp);
        if(resp.getCode() != 1000) {
            response.invalidToken();
            return response;
        }
        long id = resp.getData().getId();
        // 3. 进入业务层
        userService.doLogout(id, response);
        return response;
    }

    // 校验Token
    @Operation(summary = "token check", description = "token check")
    @GetMapping("/authorize")
    public Response<AuthorizeResp> authorize(@RequestParam(required = false) String token) {
        Response<AuthorizeResp> response = new Response<>();
        if(StringUtil.isNullOrEmpty(token)) {
            response.invalidToken();
            return response;
        }
        // 进入业务层
        userService.doAuthorize(token, response);
        return response;
    }

}
