package com.example.practice.Service;

import com.example.practice.Model.EntityType;
import com.example.practice.Model.Question;
import com.example.practice.Model.User;
import com.example.practice.tools.KeysTool;
import com.example.practice.tools.RedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FollowService {
    @Autowired
    RedisAdapter redisAdapter;
    @Autowired
    UserService userService;
    @Autowired
    QusestionService qusestionService;

    private static Logger logger = LoggerFactory.getLogger(FollowService.class);

    public boolean addFollower(int userId, int entity_type, int entity_id) {
        //粉丝   type只能是user      关注对象可能是多种type

        //某某的关注对象    某某的粉丝
        String followerKey = new KeysTool().getFollowerKey(entity_type, entity_id);
        String followeeKey = new KeysTool().getFolloweeKey(entity_type, userId);
        /*理解：followerKey   一个特定的对象的粉丝
        followeeKey       第一个参数是说某一个user某一种类型对象的关注列表
        *
        */

        //涉及到事务   要求关注时加入到关注对象里面
        try {
            Jedis jedis = redisAdapter.getJedisObject();
            Transaction transaction = redisAdapter.multiAndGetTransactionObject(jedis);
            redisAdapter.zadd(followerKey, userId);
            redisAdapter.zadd(followeeKey, entity_id);
            redisAdapter.transactionExe(jedis, transaction);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }


    public boolean unFollow(int userId, int entity_type, int entity_id) {
        String followerKey = new KeysTool().getFollowerKey(entity_type, entity_id);
        String followeeKey = new KeysTool().getFolloweeKey(entity_type, userId);

        //涉及到事务   要求关注时加入到关注对象里面
        try {
            Jedis jedis = redisAdapter.getJedisObject();
            Transaction transaction = redisAdapter.multiAndGetTransactionObject(jedis);
            redisAdapter.zrem(followerKey, userId);
            redisAdapter.zrem(followeeKey, entity_id);
            redisAdapter.transactionExe(jedis, transaction);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }


    //一个对象有多少的粉丝
    public Long countFollower(int entity_type, int entity_id) {
        String key = new KeysTool().getFollowerKey(entity_type, entity_id);
        try {
            return redisAdapter.zcard(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Long.valueOf(0);
    }

    //一个人某一种type   有多少的关注对象
    public Long countFollowee(int userId, int entity_type) {
        String key = new KeysTool().getFolloweeKey(entity_type, userId);
        try {
            return redisAdapter.zcard(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Long.valueOf(0);
    }

    //返回一个对象的所有粉丝带分页以及不带分页
    //不带分页
    public List<User> allFollower(int entity_type, int entity_id) {
        String key = new KeysTool().getFollowerKey(entity_type, entity_id);
        try {
            return fromSetToList_user(redisAdapter.zrevrang(key, 0, -1));

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    //带分页
    public List<User> allFollowerOnePage(int entity_type, int entity_id, int page) {
        String key = new KeysTool().getFollowerKey(entity_type, entity_id);
        try {
            return fromSetToList_user(redisAdapter.zrevrang(key, 10 * (page - 1), 10));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }


    //返回一个对象的所有关注对象


    public List<Object> allFolloweeOnePage(int entity_type, int userId, int page) {
        String key = new KeysTool().getFolloweeKey(entity_type, userId);
        try {
            return  fromSetToList(redisAdapter.zrevrang(key, 10 * (page - 1), 10),entity_type);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public List<Object> allFollowee(int entity_type, int userId) {
        String key = new KeysTool().getFolloweeKey(entity_type, userId);
        try {
            return fromSetToList(redisAdapter.zrevrang(key, 0, -1),entity_type);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    //得到两个人是否互相关注
    public boolean isfansxianghu(int hostuserId, int clientId) {
        String key = new KeysTool().getFollowerKey(EntityType.ENTITY_USER, hostuserId);
        try {
            if (redisAdapter.zscore(key, clientId) == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    //得到共同好友
    public List<Object> getCommenFrends(int hostuserId, int clientId) {
        String zunionkey = new KeysTool().getZunionStoreKey(EntityType.ENTITY_USER, hostuserId, EntityType.ENTITY_USER, clientId);
        String hostfolloweekey = new KeysTool().getFolloweeKey(EntityType.ENTITY_USER, hostuserId);
        String clientfolloweekey = new KeysTool().getFolloweeKey(EntityType.ENTITY_USER, clientId);
        try {
            if (redisAdapter.zunionstore(zunionkey, hostfolloweekey, clientfolloweekey) == 0) {
                return null;
            }
            return fromSetToList(redisAdapter.zrevrang(zunionkey, 0, 10),EntityType.ENTITY_USER);


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }


    private List<User> fromSetToList_user(Set<String> inputset) {
        User user = null;
        List<User> userList = new ArrayList<>();
        for (String str : inputset) {
            int userId = Integer.parseInt(str);
            user = userService.getuserbyid(userId);
            userList.add(user);
        }
        return userList;
    }

    private List<Question> fromSetToList_question(Set<String> inputset) {
        Question question = null;
        List<Question> questionList = new ArrayList<>();
        for (String str : inputset) {
            int questionId = Integer.parseInt(str);
            question = qusestionService.getQuestionById(questionId);
            questionList.add(question);
        }
        return questionList;
    }


    private List<Object> fromSetToList(Set<String> inputset, int entity_type) {

        if (entity_type == EntityType.ENTITY_USER) {

            User user = null;
            List<Object> userList = new ArrayList<>();
            for (String str : inputset) {
                int userId = Integer.parseInt(str);
                user = userService.getuserbyid(userId);
                userList.add(user);
            }
            return userList;

        }

        if (entity_type == EntityType.ENTITY_QUESTION) {
            Question question = null;
            List<Object> questionList = new ArrayList<>();
            for (String str : inputset) {
                int questionId = Integer.parseInt(str);
                question = qusestionService.getQuestionById(questionId);
                questionList.add(question);
            }
            return questionList;
        }

        logger.error(String.format("你还没有定义类型号为%d的类的set to list", entity_type));
        return null;


    }
}