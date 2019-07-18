package com.xwt.onlinemall.sso.controller;

import com.xwt.onlinemall.pojo.TbUser;
import com.xwt.onlinemall.sso.service.RegisterService;
import com.xwt.onlinemall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 注册功能Controller
 * @Author: MrXu
 * @date: 2019/7/17
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 跳转到注册页面
     * @return 注册页面
     */
    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }


    /**
     * 校验注册数据
     * @param param 校验参数
     * @param type 校验类型
     * @return E3Result
     */
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkData(@PathVariable("param") String param,
                              @PathVariable("type") Integer type)
    {
        E3Result e3Result = registerService.checkData(param, type);
        return e3Result;
    }


    /**
     * 用户注册
     * @param user 注册的用户
     * @return E3Result
     */
    @RequestMapping(value="/user/register", method= RequestMethod.POST)
    @ResponseBody
    public E3Result register(TbUser user) {
        E3Result result = registerService.register(user);
        return result;
    }
}
