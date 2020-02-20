package com.example.practice.tools;

import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPool;

public class RedisAdapter implements InitializingBean {
    private  static JedisPool pool=null;
    @Override
    public void afterPropertiesSet() throws Exception {
    pool=new JedisPool("redis://localhost:6379/0");
    }
}
