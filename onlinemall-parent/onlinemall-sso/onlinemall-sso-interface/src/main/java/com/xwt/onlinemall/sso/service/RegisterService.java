package com.xwt.onlinemall.sso.service;

import com.xwt.onlinemall.pojo.TbUser;
import com.xwt.onlinemall.utils.E3Result;

/**
 * @Description: 用户注册服务
 * @Author: MrXu
 * @date: 2019/7/18
 */
public interface RegisterService {

    /**
     * 检验数据
     * @param param 检验的参数
     * @param type 校验的类型
     * @return E3Result
     */
    E3Result checkData(String param, int type);

    /**
     * 注册用户，向数据库添加用户信息
     * @param tbUser 待注册的用户
     * @return E3Result
     */
    E3Result register(TbUser tbUser);
}
