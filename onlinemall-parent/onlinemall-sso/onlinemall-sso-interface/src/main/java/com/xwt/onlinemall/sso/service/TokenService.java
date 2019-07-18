package com.xwt.onlinemall.sso.service;

import com.xwt.onlinemall.utils.E3Result;

/**
 * @Description: 根据token查询redis中的用户信息
 * @Author: MrXu
 * @date: 2019/7/18
 */
public interface TokenService {

    /**
     * 根据token查询redis中的信息
     * @param token 指定的token
     * @return E3Result
     */
    E3Result getUserByToken(String token);

}
