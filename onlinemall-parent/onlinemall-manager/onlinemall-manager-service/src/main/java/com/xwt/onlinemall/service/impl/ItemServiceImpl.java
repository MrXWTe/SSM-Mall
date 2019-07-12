package com.xwt.onlinemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwt.onlinemall.commonpojo.EasyUIDataGridResult;
import com.xwt.onlinemall.mapper.TbItemMapper;
import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.pojo.TbItemExample;
import com.xwt.onlinemall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows){
        PageHelper.startPage(page, rows);

        // 执行查询
        TbItemExample example = new TbItemExample();
        List list = itemMapper.selectByExample(example);

        // 获取分页信息
        PageInfo<TbItem> info = new PageInfo<>(list);
        long total = info.getTotal();

        // 封装结果对象
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult(total, list);
        return easyUIDataGridResult;
    }

}

