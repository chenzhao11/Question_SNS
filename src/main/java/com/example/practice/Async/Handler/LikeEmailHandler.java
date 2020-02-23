package com.example.practice.Async.Handler;

import com.example.practice.Async.EventHandler;
import com.example.practice.Async.EventType;
import com.example.practice.Model.Event;
import com.example.practice.Model.User;
import com.example.practice.Service.UserService;
import com.example.practice.tools.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//易错每个handler必须要写component才能自动配置到后面的map里面
@Component
public class LikeEmailHandler implements EventHandler {
    @Autowired
    SendMail sendMail;
    @Autowired
    UserService userService;
    @Override
    public void work(Event event) {
        String to="zzuzhaochen@163.com";
        String subject="点赞信息";
        String template= "templates/mail/LikeTemplate.vm";
        User user=userService.getuserbyid(event.getEntity_owner());
        Map<String,Object> model=new HashMap<>();
        model.put("user",user);
        sendMail.sendHtmlMail(to,subject,template,model);
    }

    @Override
    public List<EventType> getSupportEvent() {
        return Arrays.asList(EventType.LIKE,EventType.COMMENT);
    }
}
