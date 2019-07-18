package com.xwt.onlinemall.cart.service;

import com.xwt.onlinemall.utils.E3Result;

/**
 * @Description: 购物车服务
 * @Author: MrXu
 * @date: 2019/7/18
 */

public interface CartService {

    E3Result addCart(long userId, long itemId);
}
