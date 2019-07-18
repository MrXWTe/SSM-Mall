package com.xwt.onlinemall.sso.service.impl;

import com.xwt.onlinemall.jedis.JedisClient;
import com.xwt.onlinemall.mapper.TbUserMapper;
import com.xwt.onlinemall.pojo.TbUser;
import com.xwt.onlinemall.pojo.TbUserExample;
import com.xwt.onlinemall.sso.service.LoginService;
import com.xwt.onlinemall.utils.E3Result;
import com.xwt.onlinemall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/18
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;


    /**
     * 业务逻辑：
     * 1、判断用户和密码是否正确
     * 2、如果不正确，返回登录失败
     * 3、如果正确生成token
     * 4、把用户信息写入redis; 写入数据：[token, 用户信息]
     * 5、设置key的过期时间
     * 6、返回token
     * @param username 用户名
     * @param password 密码
     * @return E3Result,包含token信息
     */
    @Override
    public E3Result login(String username, String password) {
        // 1、判断用户名密码是否正确。
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //查询用户信息
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return E3Result.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
        // 校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return E3Result.build(400, "用户名或密码错误");
        }
        // 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
        String token = UUID.randomUUID().toString();

        // 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
        // 4、使用String类型保存Session信息。可以使用“前缀:token”为key
        user.setPassword(null);
        jedisClient.set("SESSION" + ":" + token, JsonUtils.objectToJson(user));
        // 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
        jedisClient.expire("SESSION" + ":" + token, SESSION_EXPIRE);
        // 6、返回e3Result包装token。
        return E3Result.ok(token);
    }
}
