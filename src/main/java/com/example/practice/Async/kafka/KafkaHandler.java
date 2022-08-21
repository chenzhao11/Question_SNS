package com.example.practice.Async.kafka;

import com.example.practice.Async.EventType;
import com.example.practice.Async.Handler.AddFeedHandler;
import com.example.practice.Async.Handler.LikeCommentHandler;
import com.example.practice.Async.Handler.LikeEmailHandler;
import com.example.practice.Async.kafka.Handler.Handler;
import com.example.practice.Model.Event;
import com.example.practice.common.MQMessage;
import com.example.practice.tools.ConvertMQMessageToEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/20:31
 * @Description:
 */

@Component
public class KafkaHandler implements Handler {
    @Autowired AddFeedHandler addFeedHandler;
    @Autowired LikeCommentHandler likeCommentHandler;
    @Autowired LikeEmailHandler likeEmailHandler;

    @Override
    public  void work(MQMessage message){
        EventType type = message.getEvent_type();
        Event event = ConvertMQMessageToEvent.convert(message);
        if(type == EventType.LIKE){
            addFeedHandler.work(event);
            likeCommentHandler.work(event);
        }
        if(type == EventType.COMMENT || type == EventType.FOLLOW_USER || type == EventType.FOLLOW_QUESTION){
            addFeedHandler.work(event);
        }
        if(type == EventType.ALERT){
            likeEmailHandler.work(event);
        }
    }

}
