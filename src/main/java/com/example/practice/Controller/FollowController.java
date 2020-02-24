package com.example.practice.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Model.EntityType;
import com.example.practice.Model.User;
import com.example.practice.Model.UserHolder;
import com.example.practice.Model.ViewObject;
import com.example.practice.Service.FollowService;
import com.example.practice.tools.GetJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    UserHolder userHolder;



  @RequestMapping(path = "/follower")
    String getfoller(@RequestParam("userId") int userId, Model model){
     List<User> userList= followService.allFollower(EntityType.ENTITY_USER,userId);
      ViewObject viewObject=new ViewObject();
      List<ViewObject> viewObjectList=new ArrayList<>();
     if(userList!=null){
         for (User user:userList) {
             viewObject.set("user",user);
             viewObjectList.add(viewObject);
         }

         model.addAttribute("viewObjectList",viewObjectList);
     }
      return "followers";
  }


    @RequestMapping(path = "/followUser")
    @ResponseBody
    String followUser(@RequestParam("userId") int userId, Model model){
      if(userHolder.getUser()==null){
          return GetJson.getJson(999);
      }
     if(followService.addFollower(userHolder.getUser().getId(),EntityType.ENTITY_USER,userId)){
         Long followercount=followService.countFollower(EntityType.ENTITY_USER,userId);
      return JSONObject.toJSONString(1,followercount.intValue());
     }


      return GetJson.getErroJson(1,"添加关注失败！");
    }



}


