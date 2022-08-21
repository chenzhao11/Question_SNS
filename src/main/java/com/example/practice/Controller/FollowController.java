package com.example.practice.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Async.EventCreater;
import com.example.practice.Async.EventType;
import com.example.practice.Async.kafka.MQEventProducer;
import com.example.practice.Model.*;
import com.example.practice.Service.FollowService;
import com.example.practice.Service.QusestionService;
import com.example.practice.Service.UserBaseInformation;
import com.example.practice.Service.UserService;
import com.example.practice.common.Topic;
import com.example.practice.tools.GetJson;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    UserHolder userHolder;
    @Autowired
    UserBaseInformation userBaseInformation;
    @Autowired
    EventCreater eventCreater;
    @Autowired
    QusestionService qusestionService;
    @Autowired
    MQEventProducer mqEventProducer;

    @Autowired
    UserService userService;
private static final Logger logger= LoggerFactory.getLogger(FollowService.class);

    //任何人都可以看到别人的关注对象以及粉丝
    @RequestMapping(path = "/followers")
    String getfoller(@RequestParam("userId") int userId, Model model) {
        List<User> userList = followService.allFollower(EntityType.ENTITY_USER, userId);
        ViewObject viewObject = new ViewObject();
        List<ViewObject> viewObjectList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        User clientUser = userService.getuserbyid(userId);
        int followerCount = followService.countFollower(EntityType.ENTITY_USER, userId).intValue();
        model.addAttribute("clientUser", clientUser);
        model.addAttribute("followerCount", followerCount);
        if (userList != null) {
            for (User user : userList) {
                //看自己是否关注了自己or他人的粉丝.
                boolean followed = followService.isfollower(userHolder.getUser().getId(), EntityType.ENTITY_USER, user.getId());
                viewObject.set("followed", followed);
                viewObject.set("user", user);
                map = userBaseInformation.getBaseInformation(user.getId());
                viewObject.set("map", map);
                viewObjectList.add(viewObject);
            }
            model.addAttribute("viewObjectList", viewObjectList);
        }
        return "followers";
    }


    @RequestMapping(path = "/followees")
    String followees(@RequestParam("userId") int userId, Model model) {
        List<Object> userList = followService.allFollowee(EntityType.ENTITY_USER, userId);
        List<User> userList1=new ArrayList<>();
        for (Object object:userList
             ) {
            User user=(User) object;
            userList1.add(user);
        }
        List<ViewObject> viewObjectList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        User clientUser = userService.getuserbyid(userId);
        int followeeCount = followService.countFollowee(userId, EntityType.ENTITY_USER).intValue();
        model.addAttribute("clientUser", clientUser);
        model.addAttribute("followeeCount", followeeCount);
        if (userList != null) {
            for (User user: userList1) {
                ViewObject viewObject = new ViewObject();
                boolean followed = followService.isfollower(userHolder.getUser().getId(), EntityType.ENTITY_USER, user.getId());
                viewObject.set("followed", followed);
                viewObject.set("user", user);
                map = userBaseInformation.getBaseInformation(user.getId());
                viewObject.set("map", map);
                viewObjectList.add(viewObject);
            }
            model.addAttribute("viewObjectList", viewObjectList);
        }
        return "followees";
    }


    @RequestMapping(path = "/followUser", method = RequestMethod.POST)
    @ResponseBody
    String followUser(@RequestParam("userId") int userId, Model model) {
        if (userHolder.getUser() == null) {
            return GetJson.getJson(999);
        }
        if (followService.addFollower(userHolder.getUser().getId(), EntityType.ENTITY_USER, userId)) {
            Long followercount = followService.countFollower(EntityType.ENTITY_USER, userId);
            Event event=new Event().setEvent_type(EventType.FOLLOW_USER).setAction_creater_id(userHolder.getUser().getId()).setEntity_id(userId)
                    .setEntity_type(EntityType.ENTITY_USER).setEntity_owner(userId);
            // 使用redis
            //            eventCreater.push(event);
            // 使用kafka
            mqEventProducer.sendMessage(Topic.FOLLOW_USER, event);

            return GetJson.getJson(0, followercount.toString());
        }

        return GetJson.getErroJson(1, "添加关注失败！");
    }


    @RequestMapping(path = "/followQuestion")
    String followQuestion(@RequestParam(value = "questionId") int questionId){
        if(userHolder.getUser()==null){
            return "reglogin";
        }
        try {
            followService.addFollower(userHolder.getUser().getId(),EntityType.ENTITY_QUESTION,questionId);
            Event event=new Event().setEvent_type(EventType.FOLLOW_QUESTION).setAction_creater_id(userHolder.getUser().getId()).setEntity_id(questionId)
                    .setEntity_type(EntityType.ENTITY_QUESTION).setEntity_owner(qusestionService.getQuestionById(questionId).getUserId());
            // 使用redis
            //            eventCreater.push(event);
            // 使用kafka
            mqEventProducer.sendMessage(Topic.FOLLOW_QUESTION, event);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return "redirect:/index";
    }





    @RequestMapping(path = "/unfollowUser", method = RequestMethod.POST)
    @ResponseBody
    String unfollowUser(@RequestParam("userId") int userId, Model model) {
        if (userHolder.getUser() == null) {
            return GetJson.getJson(999);
        }
        if (followService.unFollow(userHolder.getUser().getId(), EntityType.ENTITY_USER, userId)) {
            Long followercount = followService.countFollower(EntityType.ENTITY_USER, userId);
            return GetJson.getJson(0, followercount.toString());
        }

        return GetJson.getErroJson(1, "取消关注失败！");
    }


}


