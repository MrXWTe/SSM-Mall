package com.xwt.onlinemall.sso.service;

import com.xwt.onlinemall.utils.E3Result;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/18
 */
public interface LoginService {

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
    E3Result login(String username, String password);
}
