package com.xwt.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwt.onlinemall.mapper.TbItemMapper;
import com.xwt.onlinemall.pojo.TbItem;
import com.xwt.onlinemall.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @Description: 测试mybatis分页插件
 * @Author: MrXu
 * @date: 2019/7/12
 */
public class PageHelperTest {

    @Test
    public void testPageHelper() throws Exception{
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper itemMapper = ac.getBean(TbItemMapper.class);
        // 设置分页信息
        PageHelper.startPage(1, 10);

        // 执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);

        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPages());
        System.out.println(list.size());
    }
}
