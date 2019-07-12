package com.xwt.onlinemall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 页面Controller，用于跳转显示页面
 * @Author: MrXu
 * @date: 2019/7/12
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable("page") String page){
        return page;
    }

}
