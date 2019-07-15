package com.xwt.onlinemall.content.service.impl;

import com.xwt.onlinemall.commonpojo.EasyUITreeNode;
import com.xwt.onlinemall.content.service.ContentCategoryService;
import com.xwt.onlinemall.mapper.TbContentCategoryMapper;
import com.xwt.onlinemall.pojo.TbContentCategory;
import com.xwt.onlinemall.pojo.TbContentCategoryExample;
import com.xwt.onlinemall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 查询内容列表的service
 * @Author: MrXu
 * @date: 2019/7/15
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {

        // 执行查询
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(example);

        // 转换成EasyUITreeNode
        List<EasyUITreeNode> treeNodes = new ArrayList<>();
        for (TbContentCategory contentCategory : tbContentCategories) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent()?"closed":"open");
            treeNodes.add(node);
        }
        return treeNodes;
    }


    @Override
    public E3Result addContentCategory(long parentId, String name) {
        // 创建新节点
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setIsParent(false);

        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        contentCategory.setSortOrder(1);

        //状态。可选值:1(正常),2(删除)
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());

        // 将新节点添加进数据库
        contentCategoryMapper.insert(contentCategory);

        // 查询父结点，如果父结点原本为子节点，设置为父结点
        TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentContentCategory.getIsParent()){
            parentContentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }
        return E3Result.ok(contentCategory);
    }
}
