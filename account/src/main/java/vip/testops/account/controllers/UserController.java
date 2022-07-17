package vip.testops.account.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.testops.account.entities.req.RegisterReq;
import vip.testops.account.entities.resp.RegisterResp;
import vip.testops.account.entities.resp.Response;
import vip.testops.account.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
