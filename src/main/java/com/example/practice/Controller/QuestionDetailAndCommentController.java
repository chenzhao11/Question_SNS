package com.example.practice.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Model.*;
import com.example.practice.Service.CommentService;
import com.example.practice.Service.QusestionService;
import com.example.practice.Service.UserService;
import com.example.practice.tools.GetJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionDetailAndCommentController {
    @Autowired
    QusestionService qusestionService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserHolder userHolder;
    @Autowired
    UserService userService;

    @RequestMapping(path = "/detail")
    String getQuestionDetail(@RequestParam("questionId") int questionId, Model model){
        Question question=qusestionService.getQuestionById(questionId);
        model.addAttribute("question",question);
        /*comment模块包含有 user以及comment的内容*/
        List<ViewObject> viewObjectList=new ArrayList<>();
        List<Comment> commentList=commentService.getCommentList(EntityType.ENTITY_QUESTION,questionId);
        for (Comment comment:commentList
             ) {

            User user=userService.getuserbyid(comment.getUserId());
            ViewObject viewObject=new ViewObject();
            viewObject.set("user",user);
            viewObject.set("comment",comment);
            viewObjectList.add(viewObject);
        }

        model.addAttribute("viewObjectList",viewObjectList);

        return "detail";
//        return "test";
    }
    @RequestMapping(path = "/addComment")
    String addComment(@RequestParam(value = "questionId" ,required = true) int questionId,@RequestParam(value = "content",required =true)  String content){
        qusestionService.updateCommentCount(questionId);
        if(userHolder.getUser()==null){
            return "redirect:/reglogin";
        }
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setEntityId(questionId);
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        comment.setStatus(0);
        comment.setUserId(userHolder.getUser().getId());
        commentService.insertComment(comment);

        return "redirect:/detail?questionId="+questionId;
    }






}



