package com.example.practice;

import com.alibaba.fastjson.JSONObject;
import com.example.practice.Model.User;
import redis.clients.jedis.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class JedisTest {

    static JedisPool pool = new JedisPool("redis://localhost:6379/0");

    static void print(int id, String str) {
        System.out.println(id + "::" + str);
    }

    static void test() {
        Jedis jedis = new Jedis();
//        jedis.sadd("user1","123");
//        System.out.println(jedis.scard("user1"));
//        jedis.lpush("list","1","2");
//        print(1,jedis.lpop("list"));
//        print(1,jedis.lpop("list"));
//        jedis.hset("testhash","zc","hello");
//        jedis.hset("testhash","zc","hello");
//        print(2,jedis.hgetAll("testhash").toString());
        //Hash
        String key1 = "key1";
        String key2 = "key2";
        jedis.hset(key1, "name", "zhaochen");
        jedis.hset(key1, "email", "zzuzhaochen@163.com");
        jedis.hset(key1, "age", "23");
        jedis.hset(key2, "name", "chengyan");
        jedis.hset(key2, "email", "zzuzhaochen@163.com");
        jedis.hset(key2, "age", "21");
        jedis.hset(key2, "gender", "female");

        print(1, jedis.hgetAll(key2).toString());
        print(2, jedis.hexists(key1, "gender").toString());
        jedis.hincrByFloat(key2, "age", 2.0);
        print(3, jedis.hget(key2, "age"));

        print(4, jedis.hscan(key2, "0").getResult().toString());
        print(5, jedis.hscan(key2, "1").getResult().toString());
        jedis.hsetnx(key2, "age", "18");
        jedis.hsetnx(key1, "gender", "male");
        print(6, jedis.hget(key2, "age"));
        print(7, jedis.hget(key1, "gender"));
        //  basic  get  and set
        jedis.set("value", "100");
        jedis.incr("value");
        print(8, jedis.get("value"));
        jedis.setex("expire", 10, "10s");
        print(9, jedis.get("expire"));

        //list   不能防止重复
        String ListKey = "ListKey";
//        for(int i=0;i<10;i++){
//            jedis.lpush(ListKey,"num"+i);
//
//        }
        print(10, jedis.llen(ListKey).toString());
        print(11, jedis.lpop(ListKey));
        print(12, jedis.lrange(ListKey, 0, 36).toString());
        jedis.linsert(ListKey, ListPosition.BEFORE, "num6", "30");
        print(13, jedis.lrange(ListKey, 0, 36).toString());


        //set
        String set_key = "likekey1";
        String set_key2 = "likekey2";
        String set_key3 = "likekey3";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(set_key, String.valueOf(i));
            jedis.sadd(set_key2, String.valueOf(i * i));

        }
        print(14, jedis.sunion(set_key, set_key2).toString());
        print(15, jedis.sdiff(set_key, set_key2).toString());
        print(16, jedis.sinter(set_key, set_key2).toString());

        jedis.sdiffstore(set_key3, set_key, set_key2);
        print(17, jedis.smembers(set_key3).toString());
        jedis.srem(set_key3, "7");
        print(18, jedis.smembers(set_key3).toString());
        jedis.sadd(set_key3, "100");
        jedis.smove(set_key3, set_key2, "100");
        print(19, jedis.smembers(set_key2).toString());


        //sorted set
        String zkey = "zkey";
        for (int i = 60; i < 100; ++i) {
            jedis.zadd(zkey, i, "member" + i);
        }
        print(20, jedis.zcard(zkey).toString());
        print(21, jedis.zcount(zkey, 70.0, 90.0).toString());
        print(22, String.valueOf(jedis.zrange(zkey, 0, 12)));
        print(23, String.valueOf(jedis.zrevrange(zkey, 0, 0)));
        print(24, String.valueOf(jedis.zscore(zkey, "member70")));
        Set<String> zset = jedis.zrangeByScore(zkey, 60, 90);
        Iterator iterator = zset.iterator();
        while (iterator.hasNext()) {
            print(25, iterator.next().toString());
        }
        print(26, zset.toString());
        Set<Tuple> tuples = jedis.zrangeByScoreWithScores(zkey, 60, 90);
        for (Tuple tuple : tuples
        ) {
            print(27, tuple.getElement() + ":" + tuple.getScore());

        }

        String zkey2 = "zkey2";
        jedis.zadd(zkey2, 1, "num1");
        jedis.zadd(zkey2, 1, "num2");
        jedis.zadd(zkey2, 1, "num3");
        jedis.zadd(zkey2, 1, "num4");
        jedis.zadd(zkey2, 1, "num5");
        jedis.zadd(zkey2, 1, "num6");
        print(28, jedis.zlexcount(zkey2, "[3", "[6").toString());
        print(42, jedis.zlexcount(zkey2, "[b", "[d").toString());
        print(29, jedis.zlexcount(zkey2, "-", "+").toString());
        print(31, jedis.zlexcount(zkey2, "[nu", "[nu").toString());
        print(32, jedis.zlexcount(zkey2, "(n", "(p").toString());
        print(33, jedis.zremrangeByLex(zkey2, "[n", "(o").toString());
        print(34, jedis.zrangeByLex(zkey2, "[n", "[o").toString());

        User user = new User();
        user.setName("zc");
        user.setSalt("hhh");
        user.setPassword("jgfdja");
        String str = JSONObject.toJSONString(user);
        jedis.set("user", str);
        User user2 = JSONObject.parseObject(jedis.get("user"), User.class);
        print(35, user2.toString());
        Transaction transaction = jedis.multi();
        transaction.zadd("qq1", 1, "num1");
        transaction.zadd("qq2", 2, "num2");
        List<Object> objects = transaction.exec();
        print(36, objects.toString());
        transaction.close();


    }


    public static void main(String[] args) {
        test();
    }
}
