package vip.testops.account.services;

import vip.testops.account.entities.req.RegisterReq;
import vip.testops.account.entities.resp.RegisterResp;
import vip.testops.account.entities.resp.Response;

public interface UserService {
    void doRegister(RegisterReq req, Response<RegisterResp> response);
}
