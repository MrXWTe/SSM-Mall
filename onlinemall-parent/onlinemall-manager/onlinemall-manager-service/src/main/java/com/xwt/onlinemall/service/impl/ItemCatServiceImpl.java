package com.xwt.onlinemall.service.impl;

import com.xwt.onlinemall.commonpojo.EasyUITreeNode;
import com.xwt.onlinemall.mapper.TbItemCatMapper;
import com.xwt.onlinemall.pojo.TbItemCat;
import com.xwt.onlinemall.pojo.TbItemCatExample;
import com.xwt.onlinemall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 获取ItemCat的service服务
 * @Author: MrXu
 * @date: 2019/7/13
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        // 执行查询
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);

        // 创建返回对象
        List<EasyUITreeNode> resList = new ArrayList<>();

        for (TbItemCat itemCat : itemCats) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            node.setState(itemCat.getIsParent()?"closed":"open");

            resList.add(node);
        }
        return resList;
    }
}
