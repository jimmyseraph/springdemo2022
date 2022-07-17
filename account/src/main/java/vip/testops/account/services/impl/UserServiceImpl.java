package vip.testops.account.services.impl;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import vip.testops.account.entities.dto.UserDTO;
import vip.testops.account.entities.req.RegisterReq;
import vip.testops.account.entities.resp.RegisterResp;
import vip.testops.account.entities.resp.Response;
import vip.testops.account.mappers.UserMapper;
import vip.testops.account.services.UserService;
import vip.testops.account.utils.EncodeUtil;
import vip.testops.account.utils.TokenUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private StringRedisTemplate stringRedisTemplate;

    @Value("${token.secret}")
    private String secret;
    @Value("${token.expire}")
    private int expire;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void doRegister(RegisterReq req, Response<RegisterResp> response) {
        // 1 将password进行sha256摘要
        String password = "";
        try {
            password = EncodeUtil.digest(req.getPassword(), "sha256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            response.internalError("digest password failed");
            return;
        }
        // 2 将用户信息插入数据库
        UserDTO userDTO = new UserDTO();
        userDTO.setAccountName(req.getName());
        userDTO.setEmail(req.getEmail());
        userDTO.setPassword(password);
        userDTO.setCreateTime(new Date());
        userDTO.setLastLoginTime(new Date());
        try {
            userMapper.addUser(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError("insert into db failed");
            return;
        }

        // 3 生成token
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", userDTO.getAccountId());
        claim.put("name", userDTO.getAccountName());
        claim.put("email", userDTO.getEmail());
        String token = "";
        try {
            token = TokenUtil.createToken(secret, claim);
        } catch (JWTCreationException e) {
            e.printStackTrace();
            response.internalError("create token failed");
            return;
        }

        // 4 将token写入redis，并设置过期时间
        String redisKey = "TOKEN:" + userDTO.getAccountId();
        stringRedisTemplate.opsForValue().set(redisKey, token, expire, TimeUnit.MINUTES);

        // 5 将response的data字段赋值
        RegisterResp resp = new RegisterResp();
        resp.setId(userDTO.getAccountId());
        resp.setName(userDTO.getAccountName());
        resp.setEmail(userDTO.getEmail());
        resp.setToken(token);
        response.successWithData(resp);
    }
}
