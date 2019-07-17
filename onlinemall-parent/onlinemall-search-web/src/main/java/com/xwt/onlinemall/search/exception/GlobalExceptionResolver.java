package com.xwt.onlinemall.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 全局异常处理器
 * @Author: MrXu
 * @date: 2019/7/17
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {


    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o,
                                         Exception e)
    {
        // 打印控制台
        e.printStackTrace();
        // 写日志
        logger.debug("测试输出的日志");
        logger.info("系统出现异常");
        logger.error("系统发生异常" + e);

        // 发邮件、发短信
        // 使用jmail，发短信使用webservice

        // 显示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
