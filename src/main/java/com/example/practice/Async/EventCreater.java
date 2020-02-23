package com.example.practice.Async;

import com.alibaba.fastjson.JSON;
import com.example.practice.Model.Event;
import com.example.practice.tools.KeysTool;
import com.example.practice.tools.RedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class EventCreater {
    /*  controller 调用后把对应的时间推入redis list里面主要是接受来自前者的信息后后把event对象序列化推入list里面
     *
     *
     * */
    private static final Logger logger = LoggerFactory.getLogger(EventCreater.class);
    @Autowired
    RedisAdapter redisAdapter;
    public int pushLikeCommentEvent(Event event) {
        String key = new KeysTool().getAsyncEventKey();
        String eventstr = JSON.toJSONString(event);

        if (event != null) {
            try {
                redisAdapter.lpush(key, eventstr);
                return 0;
            } catch (Exception e) {
                logger.error("push even redis出错！");
                e.printStackTrace();
            }

        }
        logger.error("event是null ！！");
        return 1;

    }


}
