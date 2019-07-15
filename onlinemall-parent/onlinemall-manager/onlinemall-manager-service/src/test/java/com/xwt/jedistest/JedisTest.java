package com.xwt.jedistest;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author: MrXu
 * @date: 2019/7/15
 */
public class JedisTest {
    /**
     * 测试jedis单击版连接(非连接池)
     * @throws Exception
     */
    @Test
    public void testJedis() throws Exception{
        Jedis jedis = new Jedis("218.197.194.82", 6379);
        jedis.set("test1", "test1");
        String test = jedis.get("test1");
        System.out.println(test);
        jedis.close();
    }

    /**
     * 测试jedis单击版连接(连接池)
     * @throws Exception
     */
    @Test
    public void testJedis2() throws Exception{
        JedisPool pool = new JedisPool("218.197.194.82", 6379);
        Jedis jedis = pool.getResource();
        jedis.set("test2", "test2");
        String test = jedis.get("test2");
        System.out.println(test);
        jedis.close();
        pool.close();
    }

    /**
     * 测试redis集群
     * @throws Exception
     */
    @Test
    public void testJedisCluster() throws Exception{
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("218.197.194.82", 7001));
        nodes.add(new HostAndPort("218.197.194.82", 7002));
        nodes.add(new HostAndPort("218.197.194.82", 7003));
        nodes.add(new HostAndPort("218.197.194.82", 7004));
        nodes.add(new HostAndPort("218.197.194.82", 7005));
        nodes.add(new HostAndPort("218.197.194.82", 7006));

        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("test3", "test3");
        String test = cluster.get("test3");
        System.out.println(test);
        cluster.close();
    }
}
