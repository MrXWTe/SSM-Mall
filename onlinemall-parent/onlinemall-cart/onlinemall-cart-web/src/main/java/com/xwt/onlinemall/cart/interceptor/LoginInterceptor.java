package com.xwt.onlinemall.cart.interceptor;

import com.xwt.onlinemall.pojo.TbUser;
import com.xwt.onlinemall.sso.service.TokenService;
import com.xwt.onlinemall.utils.CookieUtils;
import com.xwt.onlinemall.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户登录拦截器
 * @Author: MrXu
 * @date: 2019/7/18
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception
    {
        // 前处理，执行handler前执行此方法
        // 返回true，放行；返回false：拦截

        // 1、从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        // 2、如果没有token，直接放行
        if(StringUtils.isBlank(token))
            return true;
        // 3、取到token，调用SSO系统服务，根据token取用户信息
        E3Result e3Result = tokenService.getUserByToken(token);
        // 4、没有取到登录信息，放行
        if(e3Result.getStatus() != 200)
            return true;
        // 5、取到用户信息。表示登录
        TbUser user = (TbUser)e3Result.getData();

        // 6、把用户信息放到request中。只需在Controller中判断request中是否包含user信息。放行
        httpServletRequest.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o,
                           ModelAndView modelAndView) throws Exception
    {
        // handler执行之后，返回ModelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o,
                                Exception e) throws Exception
    {
        // 返回ModelAndView之后，可以在此处理异常
    }
}
