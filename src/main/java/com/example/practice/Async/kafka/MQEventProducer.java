package com.example.practice.Async.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Async.EventType;
import com.example.practice.Model.Event;
import com.example.practice.common.MQMessage;
import com.example.practice.common.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/12:43
 * @Description:
 */

@Component
public class MQEventProducer {
    @Autowired
    KafkaTemplate kafkaTemplate;
    @Autowired
    KafkaSendResultHandler kafkaSendResultHandler;
    public void sendMessage(String topic, Event event){
        String messageId = UUID.randomUUID().toString();
        MQMessage message = new MQMessage();
        message.setMessageId(messageId)
                .setAction_creater_id(event.getAction_creater_id())
                .setEntity_id(event.getEntity_id())
                .setEvent_type(event.getEvent_type())
                .setEntity_owner(event.getEntity_owner())
                .setMap(event.getMap());
        kafkaTemplate.setProducerListener(kafkaSendResultHandler);
        kafkaTemplate.send(topic, JSONObject.toJSON(message).toString());
    }

    public void sendMessage(String messageJson){
        MQMessage message = JSONObject.parseObject(messageJson, MQMessage.class);
        EventType eventType = message.getEvent_type();
        String topic = "";
        if(eventType == EventType.LIKE){
            topic = Topic.LIKE;
        }else if(eventType == EventType.COMMENT){
            topic = Topic.COMMENT;
        }else if(eventType == EventType.FOLLOW_USER){
            topic = Topic.FOLLOW_USER;
        }else if(eventType == EventType.FOLLOW_QUESTION){
            topic = Topic.FOLLOW_QUESTION;
        }else if(eventType == EventType.ALERT){
            topic = Topic.ALERT;
        }else{
            throw new RuntimeException("not supported event type");
        }
        kafkaTemplate.setProducerListener(kafkaSendResultHandler);
        kafkaTemplate.send(topic, messageJson);
    }
}
