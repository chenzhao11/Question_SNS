package com.example.practice.Async.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Model.Event;
import com.example.practice.common.MQMessage;
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
}
