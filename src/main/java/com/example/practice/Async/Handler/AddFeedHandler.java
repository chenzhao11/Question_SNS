package com.example.practice.Async.Handler;

import com.alibaba.fastjson.JSONObject;
import com.example.practice.Async.EventHandler;
import com.example.practice.Async.EventType;
import com.example.practice.Dao.FeedDao;
import com.example.practice.Model.EntityType;
import com.example.practice.Model.Event;
import com.example.practice.Model.Feed;
import com.example.practice.Model.UserHolder;
import com.example.practice.Service.CommentService;
import com.example.practice.Service.QusestionService;
import com.example.practice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.*;
@Component
public class AddFeedHandler implements EventHandler {

    @Autowired
    CommentService commentService;
    @Autowired
    QusestionService qusestionService;
    @Autowired
    UserService userService;
    @Autowired
    FeedDao feedDao;


    @Override
    public void work(Event event) {
        try {


        if(event.getEvent_type()==EventType.LIKE){
            Feed feed=new Feed();
            feed.setCreatedDate(new Date());
            feed.setType(EventType.LIKE.getType());
            feed.setUserId(event.getAction_creater_id());
            //需要传递的数据有：喜欢的对象的类型，ID，owner其他的数据看前端的设计
            //使用一个map转化成json串
            Map<String,Object> map=new HashMap<>();
            map.put("entityType",event.getEntity_type());
            map.put("entityId",event.getEntity_id());
            map.put("entityOwner",event.getEntity_owner());
            map.put("actUser",userService.getuserbyid(event.getAction_creater_id()));
            if(event.getEntity_type()== EntityType.ENTITY_QUESTION){
                map.put("question",qusestionService.getQuestionById(event.getEntity_id()));
            }
            if(event.getEntity_type()== EntityType.ENTITY_COMMENT){
                map.put("comment",commentService.selectCommentbyid(event.getEntity_id()));
            }

            feed.setData(JSONObject.toJSONString(map));
            feedDao.addFeed(feed);
        }
        if(event.getEvent_type()==EventType.COMMENT){
            Feed feed=new Feed();
            feed.setCreatedDate(new Date());
            feed.setType(EventType.COMMENT.getType());
            feed.setUserId(event.getAction_creater_id());
            //需要传递的数据有：喜欢的对象的类型，ID，owner其他的数据看前端的设计
            //使用一个map转化成json串
            Map<String,Object> map=new HashMap<>();
            map.put("entityType",event.getEntity_type());
            map.put("entityId",event.getEntity_id());
            map.put("entityOwner",event.getEntity_owner());
            map.put("actUser",userService.getuserbyid(
                    event.getAction_creater_id()
            ));
            map.put("question",qusestionService.getQuestionById(event.getEntity_id()));
            feed.setData(JSONObject.toJSONString(map));
            feedDao.addFeed(feed);
        }
        if(event.getEvent_type()==EventType.FOLLOW_USER){
            Feed feed=new Feed();
            feed.setCreatedDate(new Date());
            feed.setType(EventType.FOLLOW_USER.getType());
            feed.setUserId(event.getAction_creater_id());
            //需要传递的数据有：喜欢的对象的类型，ID，owner其他的数据看前端的设计
            //使用一个map转化成json串
            Map<String,Object> map=new HashMap<>();
            map.put("followToUser",userService.getuserbyid(event.getEntity_id()));
            map.put("actUser",userService.getuserbyid(event.getAction_creater_id()));
            feed.setData(JSONObject.toJSONString(map));
            feedDao.addFeed(feed);
        }
        if(event.getEvent_type()==EventType.FOLLOW_QUESTION){
            Feed feed=new Feed();
            feed.setCreatedDate(new Date());
            feed.setType(EventType.FOLLOW_QUESTION.getType());
            feed.setUserId(event.getAction_creater_id());
            //需要传递的数据有：喜欢的对象的类型，ID，owner其他的数据看前端的设计
            //使用一个map转化成json串
            Map<String,Object> map=new HashMap<>();
            map.put("entityType",event.getEntity_type());
            map.put("entityId",event.getEntity_id());
            map.put("entityOwner",event.getEntity_owner());
            map.put("actUser",userService.getuserbyid(event.getAction_creater_id()));
            map.put("question",qusestionService.getQuestionById(event.getEntity_id()));
            feed.setData(JSONObject.toJSONString(map));
            feedDao.addFeed(feed);
        }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<EventType> getSupportEvent() {
        return Arrays.asList(EventType.LIKE,EventType.COMMENT,EventType.FOLLOW_QUESTION,EventType.FOLLOW_USER);
    }
}
