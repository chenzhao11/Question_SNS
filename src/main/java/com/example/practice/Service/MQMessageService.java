package com.example.practice.Service;

import com.example.practice.Dao.MQMessageDao;
import com.example.practice.Model.MQMessage;
import com.example.practice.common.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/16:49
 * @Description:
 */
@Service
public class MQMessageService {
    @Autowired
    MQMessageDao mqMessageDao;

    public int SavaMessage(String msg){
        String messageId = UUID.randomUUID().toString();
        MQMessage message = new MQMessage()
                .setMsg(msg)
                .setMessageId(messageId)
                .setStatus(MessageStatus.INIT.ordinal())
                .setSendTimes(0)
                .setVersion(0);
        return mqMessageDao.insertMQMessage(message);
    }

    public void updateMessageStatus(MessageStatus status, String messageId){
        mqMessageDao.updateMessageStatus(status.ordinal(), messageId);
    }

    public void handleFailToSendMessage(String messageId){
        int failTimes = mqMessageDao.getSendTimesByMessageId(messageId);
        if (failTimes + 1 >= 10){
            mqMessageDao.updateMessageStatus(MessageStatus.EXCEPTION.ordinal(), messageId);
        }
        mqMessageDao.updateSendTimesByMessageId(messageId, failTimes + 1);
    }

}
