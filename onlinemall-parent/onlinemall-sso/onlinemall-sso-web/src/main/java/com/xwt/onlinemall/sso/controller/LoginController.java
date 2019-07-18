package com.xwt.onlinemall.sso.controller;

import com.xwt.onlinemall.sso.service.LoginService;
import com.xwt.onlinemall.utils.CookieUtils;
import com.xwt.onlinemall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户登录处理
 * @Author: MrXu
 * @date: 2019/7/18
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    /**
     * 跳转到登录界面
     * @return 登录界面
     */
    @RequestMapping("/page/login")
    public String showLogin(){
        return "login";
    }


    @RequestMapping(value="/user/login", method= RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password,
                          HttpServletRequest request, HttpServletResponse response) {
        // 1、接收两个参数。
        // 2、调用Service进行登录。
        E3Result result = loginService.login(username, password);
        // 判断是否登录成功
        if(result.getStatus() == 200){
            // 3、从返回结果中取token，写入cookie。Cookie要跨域。
            String token = result.getData().toString();
            CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
        }

        // 4、响应数据。Json数据。e3Result，其中包含Token。
        return result;
    }
}
