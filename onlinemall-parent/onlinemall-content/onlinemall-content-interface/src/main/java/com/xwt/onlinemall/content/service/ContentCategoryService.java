package com.xwt.onlinemall.content.service;

import com.xwt.onlinemall.commonpojo.EasyUITreeNode;
import com.xwt.onlinemall.utils.E3Result;

import java.util.List;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/15
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCategoryList(long parentId);
    E3Result addContentCategory(long parentId, String name);
}
