package com.xwt.onlinemall.service;

import com.xwt.onlinemall.commonpojo.EasyUITreeNode;

import java.util.List;

/**
 * @Description: 获取ItemCat的service服务接口
 * @Author: MrXu
 * @date: 2019/7/13
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
