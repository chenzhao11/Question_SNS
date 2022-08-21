package com.example.practice.Controller;

import com.example.practice.Model.*;
import com.example.practice.Service.CommentService;
import com.example.practice.Service.FollowService;
import com.example.practice.Service.QusestionService;
import com.example.practice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserService userService;
    @Autowired
    QusestionService qusestionService;
    @Autowired
    FollowService followService;
    @Autowired
    UserHolder userHolder;
    @Autowired
    CommentService commentService;


    List<ViewObject> getViewObjectList(int id, int offset, int limit) {
        List<Question> questionList = qusestionService.latestquestion(id, offset, limit);
        List<ViewObject> viewObjectList = new ArrayList<>();
        for (Question question : questionList
        ) {
            ViewObject viewObject = new ViewObject();
            User user = userService.getuserbyid(question.getUserId());
            int concern=followService.countFollower(EntityType.ENTITY_QUESTION,question.getId()).intValue();
            viewObject.set("user", user);
            viewObject.set("question", question);
            viewObject.set("concern", concern);
            viewObjectList.add(viewObject);

        }
        return viewObjectList;

    }

    @RequestMapping(path = {"/","/index"})
    String indexpage(Model model) {
        List<ViewObject> objectList = getViewObjectList(0, 0, 20);
        model.addAttribute("viewObject", objectList);
        return "index";
    }

    @RequestMapping(path = "/profile")
    String authorlatest(@RequestParam("userId") int userid, Model model) {
        //        List<ViewObject> objectList = getViewObjectList(userid, 0, 10);
        //        model.addAttribute("viewObject", objectList);
        ViewObject viewObject=new ViewObject();
        User user=userService.getuserbyid(userid);
        boolean followed=followService.isfollower(userHolder.getUser().getId(), EntityType.ENTITY_USER,userid);
        int followerCount=followService.countFollower(EntityType.ENTITY_USER,userid).intValue();
        int followeeCount=followService.countFollowee(userid,EntityType.ENTITY_USER).intValue();
        int commentCount=commentService.countComment(userid);
        viewObject.set("user",user);
        viewObject.set("followed",followed);
        viewObject.set("followerCount",followerCount);
        viewObject.set("followeeCount",followeeCount);
        viewObject.set("commentCount",commentCount);


        model.addAttribute("viewObject",viewObject);



        return "profile";
    }

    //    @RequestMapping(path = {"/test"})
    //    @ResponseBody
    //    String indextest( HttpSession session){
    //        List<ViewObject> objectList=getViewObjectList(0,20,10);
    //        StringBuffer stringBuffer=new StringBuffer();
    //        for (ViewObject object:objectList
    //             ) {
    //            stringBuffer.append(object.get("user").toString()+"==========="+object.get("question").toString()
    //            +"<br>");
    //
    //        }
    //        return  stringBuffer.toString();
    //
    //    }

    @ExceptionHandler
    @ResponseBody
    String exceptionhandler(Exception e) {
        return "some erro hapens!" + "<br>" +  "<br>" + e.toString();
    }


}


