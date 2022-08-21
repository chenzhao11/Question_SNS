package com.example.practice.Async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Model.Event;
import com.example.practice.tools.KeysTool;
import com.example.practice.tools.RedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    /*
     从redis  list里面取event出来  读取event里面的type类型通知对其感兴趣的handler来处理

     异步处理的关键以及难点
     */
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> map = new HashMap<EventType, List<EventHandler>>();

    @Autowired
    RedisAdapter redisAdapter;
    @Autowired
    KeysTool keysTool;

    /*
    先初始化map   再创建线程不停处理取event出来
     */
    @Override
    public void afterPropertiesSet() throws Exception {
//        初始化map  实现自动配置handler
        Map<String, EventHandler> beansmap = applicationContext.getBeansOfType(EventHandler.class);
        for (Map.Entry<String, EventHandler> entry : beansmap.entrySet()) {
            logger.info(entry.getKey());//  entry.getKey()   ==>likeCommentHandler
            List<EventType> supportEventTypes = entry.getValue().getSupportEvent();
            for (EventType eventType : supportEventTypes) {
                if (map.get(eventType) == null) {
                    map.put(eventType, new ArrayList<EventHandler>());
                }
                map.get(eventType).add(entry.getValue());

            }
        }
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<String> singleEventList = redisAdapter.brpop(keysTool.getAsyncEventKey());
                    for (String str : singleEventList
                    ) {
                        if (str.equals(keysTool.getAsyncEventKey())) {
                            continue;
                        }
                        Event event = JSONObject.parseObject(str, Event.class);
                        if (map.get(event.getEvent_type()) == null)
                        {
                            logger.error("非法事件！！");
                            break;
                        }
                      List<EventHandler> needToExe = map.get(event.getEvent_type());
                        for (EventHandler eventHandler:needToExe
                             ) {
                            eventHandler.work(event);
                        }
                    }
                }
            }
        });





    //提交多个线程来处理异步
      executorService.submit(new Runnable() {
        @Override
        public void run() {
            while (true) {
                List<String> singleEventList = redisAdapter.brpop(keysTool.getAsyncEventKey());
                for (String str : singleEventList
                ) {
                    if (str.equals(keysTool.getAsyncEventKey())) {
                        continue;
                    }
                    Event event = JSONObject.parseObject(str, Event.class);
                    if (map.get(event.getEvent_type()) == null)
                    {
                        logger.error("非法事件！！");
                        break;
                    }
                    List<EventHandler> needToExe = map.get(event.getEvent_type());
                    for (EventHandler eventHandler:needToExe
                    ) {
                        eventHandler.work(event);
                    }
                }
            }
        }
    });



        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<String> singleEventList = redisAdapter.brpop(keysTool.getAsyncEventKey());
                    for (String str : singleEventList
                    ) {
                        if (str.equals(keysTool.getAsyncEventKey())) {
                            continue;
                        }
                        Event event = JSONObject.parseObject(str, Event.class);
                        if (map.get(event.getEvent_type()) == null)
                        {
                            logger.error("非法事件！！");
                            break;
                        }
                        List<EventHandler> needToExe = map.get(event.getEvent_type());
                        for (EventHandler eventHandler:needToExe
                        ) {
                            eventHandler.work(event);
                        }
                    }
                }
            }
        });




}



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
