package com.example.practice.Async.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Service.MQMessageService;
import com.example.practice.common.MQMessage;
import com.example.practice.common.MessageStatus;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/14:26
 * @Description:
 */
@Component
public class KafkaSendResultHandler implements ProducerListener {
    private static final Logger log = LoggerFactory.getLogger(KafkaSendResultHandler.class);
    @Autowired
    MQMessageService messageService;

    // 发送成功将DB中message的状态更新成已发送
    @Override public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        MQMessage message = JSONObject.parseObject(producerRecord.value().toString(), MQMessage.class);
        messageService.updateMessageStatus(MessageStatus.SENT, message.getMessageId());
    }
    // 发送失败，更新DB中message的失败次数
    @Override public void onError(ProducerRecord producerRecord, RecordMetadata recordMetadata,
                                  Exception exception) {
        log.error("发送message到MQ失败：%s", exception);
        MQMessage message = JSONObject.parseObject(producerRecord.value().toString(), MQMessage.class);
        messageService.handleFailToSendMessage(message.getMessageId());
    }
}
