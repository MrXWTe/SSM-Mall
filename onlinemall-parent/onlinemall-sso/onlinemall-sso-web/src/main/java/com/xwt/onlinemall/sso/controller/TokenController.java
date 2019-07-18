package com.xwt.onlinemall.sso.controller;

import com.xwt.onlinemall.sso.service.TokenService;
import com.xwt.onlinemall.utils.E3Result;
import com.xwt.onlinemall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 根据token查询用户信息
 * @Author: MrXu
 * @date: 2019/7/18
 */
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;


    /*@RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE *//*"application/json;charset=utf-8"*//*)
    @ResponseBody
    public String getUserByToken(@PathVariable("token")String token, String callback){
        E3Result result = tokenService.getUserByToken(token);
        // 响应结果之前，判断是否是jsonp请求
        if(StringUtils.isNoneBlank(callback)){
            // 把结果封装成js语句响应
            return callback + "(" + JsonUtils.objectToJson(result) + ");";
        }
        return JsonUtils.objectToJson(result);
    }*/

    @RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE /*"application/json;charset=utf-8"*/)
    @ResponseBody
    public Object getUserByToken(@PathVariable("token")String token, String callback){
        E3Result result = tokenService.getUserByToken(token);
        // 响应结果之前，判断是否是jsonp请求
        if(StringUtils.isNoneBlank(callback)){
            // 把结果封装成js语句响应
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
