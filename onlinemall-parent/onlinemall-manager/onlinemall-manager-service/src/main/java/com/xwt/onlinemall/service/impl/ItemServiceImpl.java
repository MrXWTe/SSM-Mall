package com.xwt.onlinemall.service.impl;

import com.xwt.onlinemall.mapper.TbItemMapper;
import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/11
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem getItemById(long id) {
        TbItem item = itemMapper.selectByPrimaryKey(id);
        return item;
    }

}

