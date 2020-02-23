package com.example.practice.Async.Handler;

import com.example.practice.Async.EventHandler;
import com.example.practice.Async.EventType;
import com.example.practice.Model.AdminId;
import com.example.practice.Model.Event;
import com.example.practice.Model.Message;
import com.example.practice.Model.User;
import com.example.practice.Service.MessageService;
import com.example.practice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeCommentHandler implements EventHandler {
    //    private  List<EventType> eventTypes=new ArrayList<>();
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;


    @Override
    public void work(Event event) {
        Message message = new Message();
        User actor = userService.getuserbyid(event.getAction_creater_id());
        User owner = userService.getuserbyid(event.getEntity_owner());
        message.setContent(String.format("%s 喜欢了你的评论！", actor.getName()));
        message.setToid(event.getEntity_owner());
        message.setFromid(AdminId.ADMINID);
        message.setCreatedDate(new Date());
        String conversationId;
        if ( AdminId.ADMINID < event.getEntity_owner()) {
            conversationId = "" + AdminId.ADMINID + "_" + event.getEntity_owner();
        } else {
            conversationId = "" + event.getEntity_owner()+ "_" + AdminId.ADMINID;
        }
        message.setConversationId(conversationId);
        messageService.sendMessage(message);
    }

    @Override
    public List<EventType> getSupportEvent() {
        return Arrays.asList(EventType.LIKE);
    }
}
