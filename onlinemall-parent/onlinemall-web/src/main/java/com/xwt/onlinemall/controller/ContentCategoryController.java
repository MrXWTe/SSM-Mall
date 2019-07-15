package com.xwt.onlinemall.controller;

import com.xwt.onlinemall.commonpojo.EasyUITreeNode;
import com.xwt.onlinemall.content.service.ContentCategoryService;
import com.xwt.onlinemall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: 查询内容列表的Controller
 * @Author: MrXu
 * @date: 2019/7/15
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(
            @RequestParam(value = "id", defaultValue = "0") long parentId)
    {
        List<EasyUITreeNode> contentCategoryList = contentCategoryService.getContentCategoryList(parentId);
        return contentCategoryList;
    }

    @RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContentCategory(long parentId, String name){
        E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
        return e3Result;
    }

}
