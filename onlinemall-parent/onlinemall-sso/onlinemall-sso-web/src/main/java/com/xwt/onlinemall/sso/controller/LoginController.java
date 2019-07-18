package com.xwt.onlinemall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 用户登录处理
 * @Author: MrXu
 * @date: 2019/7/18
 */
@Controller
public class LoginController {

    @RequestMapping("/page/login")
    @ResponseBody
    public String showLogin(){
        return "login";
    }
}
