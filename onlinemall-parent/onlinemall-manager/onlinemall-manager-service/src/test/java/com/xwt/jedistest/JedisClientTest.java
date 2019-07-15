package com.xwt.jedistest;

import com.xwt.onlinemall.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/15
 */
public class JedisClientTest {

    /**
     * 测试redisClient连接
     * @throws Exception
     */
    @Test
    public void testJedisClient() throws Exception{
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = ac.getBean(JedisClient.class);
        jedisClient.set("test4", "test4");
        System.out.println(jedisClient.get("test4"));
    }
}
