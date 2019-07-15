package com.xwt.onlinemall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 前端主页面Controller
 * @Author: MrXu
 * @date: 2019/7/15
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String showIndexPage(){
        return "index";
    }
}
