package com.xwt.onlinemall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 注册功能Controller
 * @Author: MrXu
 * @date: 2019/7/17
 */
@Controller
public class RegitsterController {

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }
}
