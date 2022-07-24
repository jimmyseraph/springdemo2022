package vip.testops.account.services;

import vip.testops.account.entities.req.LoginReq;
import vip.testops.account.entities.req.RegisterReq;
import vip.testops.account.entities.resp.AuthorizeResp;
import vip.testops.account.entities.resp.RegisterResp;
import vip.testops.account.entities.resp.Response;

public interface UserService {
    void doRegister(RegisterReq req, Response<RegisterResp> response);
    void doLogin(LoginReq req, Response<RegisterResp> response);
    void doLogout(long id, Response<?> response);
    void doAuthorize(String token, Response<AuthorizeResp> response);
}
