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
    public List<Message> allMessageList(int toUserId){
       return  messageDao.selectMessageList(toUserId);
    }
    public int getUnread(int toUserId,String conversationId){
       return messageDao.getUnread(toUserId,conversationId);
    }
    public void updateHasRead(int toid,String conversationid){
       messageDao.updateHasRead(toid,1,conversationid);
    }


}
