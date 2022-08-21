package com.example.practice.Async.compensation;

import com.example.practice.Async.kafka.MQEventProducer;
import com.example.practice.Dao.MQMessageDao;
import com.example.practice.Model.MQMessage;
import com.example.practice.Service.MQMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/23/21:27
 * @Description:
 */
@Component
@Slf4j
public class MessageCompensation {
    @Autowired MQEventProducer  mqEventProducer;
    @Autowired MQMessageService messageService;
    @Scheduled(cron = "0 0 */1 * *?")
    public void messageCompensationHelper(){
        //从数据库中查询状态时未提交的message重新发送
        List<MQMessage> messages = messageService.getInitMQMessages();
        for (MQMessage message : messages){
            // 失败的时候有绑定有监听函数自动对状态进行更新
            try {
                mqEventProducer.sendMessage(message.getMsg());
            }catch (Exception e){
                log.error(e.getMessage());
                messageService.handleFailToSendMessage(message.getMessageId());
            }
        }
    }
}
