package com.xwt.onlinemall.activemq;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/17
 */
public class MessageConsumer {

    /**
     * 初始化方法
     */
    @Before
    public void init(){
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-activemq.xml");

    }

    @Test
    public void msgConsumer() throws Exception{
        System.in.read();
    }
}
