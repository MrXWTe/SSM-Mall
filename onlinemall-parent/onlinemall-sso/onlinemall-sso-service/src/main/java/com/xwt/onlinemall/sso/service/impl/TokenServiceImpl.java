package com.xwt.onlinemall.sso.service.impl;

import com.xwt.onlinemall.jedis.JedisClient;
import com.xwt.onlinemall.pojo.TbUser;
import com.xwt.onlinemall.sso.service.TokenService;
import com.xwt.onlinemall.utils.E3Result;
import com.xwt.onlinemall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description: 根据token查询redis中的用户信息
 * @Author: MrXu
 * @date: 2019/7/18
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {
        // 根据token取redis的用户信息
        String s = jedisClient.get("SESSION:" + token);

        // 如果取不到用户信息
        if(StringUtils.isBlank(s)) {
            return E3Result.build(201, "用户登录已经过期");
        }

        // 更新过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        // 如果取到了用户信息
        TbUser user = JsonUtils.jsonToPojo(s, TbUser.class);

        return E3Result.ok(user);

    }
}
