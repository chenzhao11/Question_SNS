package com.example.practice.Service;

import com.example.practice.Model.EntityType;
import com.example.practice.Model.User;
import com.example.practice.Model.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Component
public class UserBaseInformation {
    @Autowired
    FollowService followService;
    @Autowired
    UserHolder userHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    public Map<String,Object> getBaseInformation(int userid){
        Map<String,Object> resultMap=new HashMap<>();
        int followerCount=followService.countFollower(EntityType.ENTITY_USER,userid).intValue();
        int followeeCount=followService.countFollowee(userid,EntityType.ENTITY_USER).intValue();
        int commentCount=commentService.countComment(userid);
        resultMap.put("followerCount",followerCount);
        resultMap.put("followeeCount",followeeCount);
        resultMap.put("commentCount",commentCount);
        return resultMap;

    }

}
