package com.xwt.onlinemall.cart.service.impl;

import com.xwt.onlinemall.cart.service.CartService;
import com.xwt.onlinemall.jedis.JedisClient;
import com.xwt.onlinemall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description: 购物车处理服务
 * @Author: MrXu
 * @date: 2019/7/18
 */
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;


    @Override
    public E3Result addCart(long userId, long itemId) {
        // 向redis中添加购物车

        //jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId+"");
        return null;
    }
}
