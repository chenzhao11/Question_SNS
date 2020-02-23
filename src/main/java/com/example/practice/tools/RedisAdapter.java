package com.example.practice.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;


@Service
public class RedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(RedisAdapter.class);
    private static JedisPool pool = null;

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("redis   sadd  错误！");
        } finally {
            jedis.close();
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("redis   srem  错误！");
        } finally {
            jedis.close();
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("redis   sismember 错误！");
        } finally {
            jedis.close();
        }
        return false;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("redis   scard错误！");
        } finally {
            jedis.close();
        }
        return 0;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("redis  lpush错误！");
        } finally {
            jedis.close();
        }
        return 0;
    }

    public List<String> brpop(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();

            return jedis.brpop(0,key);

        } catch (Exception e) {
            logger.error("redis  brpop错误！");
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/1");
    }


}
