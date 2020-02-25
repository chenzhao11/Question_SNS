package com.example.practice.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


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



    public Long zadd (String key,int userId) {
        Jedis jedis = null;
        double date=new Date().getTime();
        try {
            jedis = pool.getResource();
            return jedis.zadd(key,date,userId+"");

        } catch (Exception e) {
            logger.error("redis  zadd错误！");
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return Long.valueOf(0);
    }

    public Long zrem (String key,int userId) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();

            return jedis.zrem(key,String.valueOf(userId));

        } catch (Exception e) {
            logger.error("redis  zrem错误！");
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return Long.valueOf(0);
    }

    public Set<String> zrang (String key, int offset, int count) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrange(key,offset,offset+count-1);

        } catch (Exception e) {
            logger.error("redis  zrang错误！");
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }


    public Set<String> zrevrang (String key, int offset, int count) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key,offset,offset+count-1);

        } catch (Exception e) {
            logger.error("redis  zrevrang错误！");
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }


    public Long zcard (String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);

        } catch (Exception e) {
            logger.error("redis  zcard错误！");
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return Long.valueOf(0);
    }

    public Transaction multiAndGetTransactionObject (Jedis jedis) {
        try {
            return jedis.multi();

        } catch (Exception e) {
            logger.error("redis multi错误！");
            e.printStackTrace();
        }
        return null;
    }

    public Jedis getJedisObject () {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis;

        } catch (Exception e) {
            logger.error(" get  jedis错误！");
            e.printStackTrace();

        }
        return null;
    }
  //重要  transaction要判断是否操作完成  否则要回滚     并且最后finally要    关闭transaction   要是Jedis是pool里面的要close
    public List<Object> transactionExe (Jedis jedis, Transaction transaction) {
        try {
            return transaction.exec();

        } catch (Exception e) {

            logger.error(" get  jedis错误！");
            e.printStackTrace();
            transaction.discard();

        } finally {

            if (transaction != null) {
                transaction.close();
                if (jedis != null) {
                    jedis.close();
                }
            }
            return null;
        }
    }
//一个对象的粉丝里面是否有某一用户
    public Double zscore(String key,int userId){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.zscore(key,String.valueOf(userId));
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        finally {
            jedis.close();
        }
        return null;
    }
    public Long zunionstore (String zunionkey,String key1,String key2){
        Jedis jedis=null;

        try{
            jedis=pool.getResource();
            return jedis.zunionstore(zunionkey,key1,key2);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        finally {
            jedis.close();
        }
        return Long.valueOf(0);
    }





    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/1");
    }


}
