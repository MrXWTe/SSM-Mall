package com.xwt.onlinemall.sso.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xwt.onlinemall.sso.service.RegisterService;
import com.xwt.onlinemall.mapper.TbUserMapper;
import com.xwt.onlinemall.pojo.TbUser;
import com.xwt.onlinemall.pojo.TbUserExample;
import com.xwt.onlinemall.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description: 用户注册服务的实现
 * @Author: MrXu
 * @date: 2019/7/18
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;


    /**
     * 检验数据
     * @param param 检验的参数
     * @param type 校验的类型
     * @return E3Result
     */
    @Override
    public E3Result checkData(String param, int type) {
        // 1、从tb_user表中查询数据
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        // 2、查询条件根据参数动态生成。
        //1、2、3分别代表username、phone、email
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            return E3Result.build(400, "非法的参数");
        }

        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        // 3、判断查询结果，如果查询到数据返回false。
        if (list == null || list.size() == 0) {
            // 4、如果没有返回true。
            return E3Result.ok(true);
        }
        // 5、使用E3Result包装，并返回。
        return E3Result.ok(false);
    }


    /**
     * 注册用户，向数据库添加用户信息
     * @param user 待注册的用户
     * @return E3Result
     */
    @Override
    public E3Result register(TbUser user) {

        E3Result result = null;

        // 1、使用TbUser接收提交的请求。
        if (StringUtils.isBlank(user.getUsername())) {
            // 校验用户名是否为空
            return E3Result.build(400, "用户名不能为空");
        }else{
            // 校验数据是否在数据库中存在
            result = checkData(user.getUsername(), 1);
            if (!(boolean) result.getData()) {
                return E3Result.build(400, "此用户名已经被使用");
            }
        }


        // 校验密码是否为空
        if (StringUtils.isBlank(user.getPassword())) {
            return E3Result.build(400, "密码不能为空");
        }


        if (StringUtils.isBlank(user.getPhone())) {
            // 校验电话是否为空
            return E3Result.build(400, "用户名不能为空");
        }else{
            // 校验电话是否在数据库中存在
            result = checkData(user.getPhone(), 2);
            if (!(boolean) result.getData()) {
                return E3Result.build(400, "此手机号已经被使用");
            }
        }
        // 2、补全TbUser其他属性。
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // 3、密码要进行MD5加密。
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        // 4、把用户信息插入到数据库中。
        userMapper.insert(user);
        // 5、返回e3Result。
        return E3Result.ok();
    }
}
