package com.vleus.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @author vleus
 * @date 2021年07月03日 23:52
 */
public class JedisDemo1 {

    public static void main(String[] args) {

        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.37.139",6379);

        System.out.println(jedis.ping());

    }



    //操作key的部分
    @Test
    public void demo1() {

        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.37.139",6379);

        //添加数据
        jedis.set("name", "lucy");

        String name = jedis.get("name");
        System.out.println(name);

        //设置多个key-value
        jedis.mset("k1", "v1", "k2", "v2");

        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

//        jedis.flushDB();
    }

    //操作list的部分
    @Test
    public void demo2() {

        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.37.139",6379);

        jedis.lpush("key1", "lcuy", "marry", "jack");
        List<String> values = jedis.lrange("key1", 0, -1);
        System.out.println(values);
    }

    //操作set的部分
    @Test
    public void demo3() {

        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.37.139",6379);

        jedis.sadd("sname", "lucy");
        jedis.sadd("sname", "marry");
        Set<String> names = jedis.smembers("sname");

        System.out.println(names);
    }

    //操作hash的部分
    @Test
    public void demo4() {

        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.37.139", 6379);

        jedis.hset("users", "age", "20");
        String hget = jedis.hget("users", "age");
        System.out.println(hget);
    }

    //操作zset的部分
    @Test
    public void demo5() {

        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.37.139", 6379);

        jedis.zadd("china", 100d, "shanghai");

        Set<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);
    }
}