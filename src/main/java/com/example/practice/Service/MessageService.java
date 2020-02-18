package com.example.practice.Service;

import com.example.practice.Dao.MessageDao;
import com.example.practice.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class MessageService {
    @Autowired
    MessageDao messageDao;
   public List<Message> getMessageByConversationId(String conversationId){
        return  messageDao.selectMessage(conversationId);
    }
    public void sendMessage(Message message){
       messageDao.insertMessage(message);
    }
}
