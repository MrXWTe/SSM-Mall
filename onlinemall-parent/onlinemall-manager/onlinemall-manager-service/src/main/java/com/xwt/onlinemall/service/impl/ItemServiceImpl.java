package com.xwt.onlinemall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwt.onlinemall.commonpojo.EasyUIDataGridResult;
import com.xwt.onlinemall.mapper.TbItemDescMapper;
import com.xwt.onlinemall.mapper.TbItemMapper;
import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.pojo.TbItemDesc;
import com.xwt.onlinemall.pojo.TbItemExample;
import com.xwt.onlinemall.service.ItemService;
import com.xwt.onlinemall.utils.E3Result;
import com.xwt.onlinemall.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private TbItemDescMapper itemDescMapper;

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

    @Override
    public E3Result addItem(TbItem item, String desc) {
        // 生成商品ID
        long itemId = IDUtils.genItemId();
        // 补全Item属性
        item.setId(itemId);
        // 向商品表插入数据
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 创建商品描述表对应的pojo对象
        itemMapper.insert(item);

        // 向商品描述表插入数据
        TbItemDesc itemDesc = new TbItemDesc();
        // 补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        // 向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        // 返回成功
        return E3Result.ok();
    }
}

