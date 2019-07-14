package com.xwt.onlinemall.controller;

import com.xwt.onlinemall.commonpojo.EasyUIDataGridResult;
import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.service.ItemService;
import com.xwt.onlinemall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/11
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(int page, int rows){
        return itemService.getItemList(page, rows);
    }

    /**
     * 商品添加功能
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem item, String desc){
        E3Result e3Result = itemService.addItem(item, desc);
        return e3Result;
    }

}
