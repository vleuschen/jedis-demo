package com.vleus.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author vleus
 * @date 2021年07月04日 10:44
 */
public class PhoneCode {

    public static void main(String[] args) {
//        System.out.println(getCode());
//        verifyCode("15285226936");
        verifyCode("15285226936");
    }

    //1、生成六位数字的验证码
    public static String getCode() {

        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            code += i1;
        }
        return code;
    }

    //2.每个手机每天只能发送三次，验证码放到redis中，设置过期时间
    public static void verifyCode(String phone) {

        //创建Jedis对象，连接Redis
        Jedis jedis = new Jedis("192.168.37.139", 6379);

        //拼接key
        //手机发送次数
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";

        //每个手机每天只能发送三次
        String count = jedis.get(countKey);
        if (count == null) {
            //没有发送次数，第一次发送
            //设置发送次数为1
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            //发送次数+1
            jedis.incr(countKey);
        } else if(Integer.parseInt(count) > 2) {
            //发送三次，不能再发送
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
        }

        //发送验证码，要放到redis里面
        String vcode = getCode();
        jedis.setex(codeKey, 120, vcode);
        jedis.close();
    }

    //验证码校验
    public static void getRedisCode(String phone,String code) {

        //从redis中获取验证码
        //创建Jedis对象，连接Redis
        Jedis jedis = new Jedis("192.168.37.139", 6379);

        //验证码key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);

        //做判断
        if (redisCode.equals(code)) {
            System.out.println("验证码验证成功");
        }else{
            System.out.println("验证码验证失败");
        }
    }

}