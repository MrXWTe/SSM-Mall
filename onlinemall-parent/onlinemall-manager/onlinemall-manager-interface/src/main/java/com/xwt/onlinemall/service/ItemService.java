package com.xwt.onlinemall.service;

import com.xwt.onlinemall.commonpojo.EasyUIDataGridResult;
import com.xwt.onlinemall.pojo.TbItem;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/11
 */
public interface ItemService {

    TbItem getItemById(long id);
    EasyUIDataGridResult getItemList(int page, int rows);
}
