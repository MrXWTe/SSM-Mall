package com.xwt.onlinemall.controller;

import com.xwt.onlinemall.commonpojo.EasyUITreeNode;
import com.xwt.onlinemall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: 获取itemCatList的Controller
 * @Author: MrXu
 * @date: 2019/7/13
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCat(@RequestParam(value = "id", defaultValue = "0") long parentId){
        List<EasyUITreeNode> itemCatList = itemCatService.getItemCatList(parentId);
        return itemCatList;
    }
}
