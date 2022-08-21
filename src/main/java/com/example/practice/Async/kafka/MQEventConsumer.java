package com.example.practice.Async.kafka;

import com.alibaba.fastjson.JSONObject;
import com.example.practice.common.MQMessage;
import com.example.practice.common.Topic;
import com.example.practice.tools.MessageIdHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/12:44
 * @Description:
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class MQEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MQEventConsumer.class);
    /**
     * 顺序消费并发级别
     */
    private Integer concurrent = 6;

    private KafkaConsumerPool<MQMessage> kafkaConsumerPool;
    @Autowired
    KafkaHandler kafkaHandler;

    /**
     * 初始化顺序消费池
     */
    @PostConstruct
    public void init(){
        KafkaSortConsumerConfig<MQMessage> config = new KafkaSortConsumerConfig<>();
        config.setBizName("Message");
        config.setBizService(kafkaHandler::work);
        config.setConcurrentSize(concurrent);
        kafkaConsumerPool = new KafkaConsumerPool<>(config);
    }


    @KafkaListener(topics = {"like", "comment", "follow_user", "follow_question", "alert"})
    public void handleMessage(List<ConsumerRecord<?, ?>> records, Acknowledgment ack){
        if(records.isEmpty()){
            return;
        }

        records.forEach(consumerRecord->{
            MQMessage message = JSONObject.parseObject(consumerRecord.value().toString(), MQMessage.class);
            kafkaConsumerPool.submitTask(MessageIdHelper.getId(message.getMessageId()),message);
        });

        // 当线程池中任务处理完成的计数达到拉取到的记录数时提交
        // 注意这里如果存在部分业务阻塞时间很长，会导致位移提交不上去，务必做好一些熔断措施
        while (true){
            if(records.size() == kafkaConsumerPool.getPendingOffsets().get()){
                ack.acknowledge();
//                log.info("offset提交：{}",records.get(records.size()-1).offset());
                kafkaConsumerPool.getPendingOffsets().set(0L);
                break;
            }
        }


    }
}