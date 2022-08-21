package com.example.practice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/10:35
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = PracticeApplication.class)
public class KafkaTest {
   @Autowired
   private kafkaProducer kafkaProducer;
    @Test
    public void testKafka() throws InterruptedException {
        kafkaProducer.sendMessage("test","hello");
        kafkaProducer.sendMessage("test","world");
        Thread.sleep(1000 * 10);

    }
}
@Component
class kafkaProducer{
    @Autowired
    private KafkaTemplate kafkaTemplate;
    public void sendMessage(String topic, String content){
        kafkaTemplate.send(topic, content);
    }
}

@Component
class kafkaConsumer{
    @KafkaListener(topics = { "test"})
    public void handleMessage(ConsumerRecord record){
        System.out.println(record.value());
    }
}
