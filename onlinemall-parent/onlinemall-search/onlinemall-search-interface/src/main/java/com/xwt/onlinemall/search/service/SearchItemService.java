package com.xwt.onlinemall.search.service;

import com.xwt.onlinemall.utils.E3Result;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/16
 */
public interface SearchItemService {
    E3Result importAllItems();
    E3Result importOneItem(long itemId);
}
