package com.example.practice.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.practice.Async.EventCreater;
import com.example.practice.Async.EventType;
import com.example.practice.Model.*;
import com.example.practice.Service.CommentService;
import com.example.practice.Service.QusestionService;
import com.example.practice.Service.UserService;
import com.example.practice.tools.GetJson;
import com.example.practice.tools.KeysTool;
import com.example.practice.tools.RedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    RedisAdapter redisAdapter;
    @Autowired
    EventCreater eventCreater;
private static final Logger logger=LoggerFactory.getLogger(QuestionDetailAndCommentController.class);

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
            String likekey= new KeysTool().getLikeCommentKey(EntityType.ENTITY_COMMENT,comment.getId());
            viewObject.set("liked",redisAdapter.scard(likekey));
            viewObject.set("likeCount",redisAdapter.scard(likekey));
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

        //评论
//        Event event=new Event();
//        event.setAction_creater_id(userHolder.getUser().getId());
//        event.setEntity_type(EntityType.ENTITY_QUESTION);
//        event.setEntity_id(questionId);
//        event.setEntity_owner(qusestionService.getQuestionById(questionId).getUserId());
//        event.setEvent_type(EventType.COMMENT);
//
//        eventCreater.pushLikeCommentEvent(event);



        return "redirect:/detail?questionId="+questionId;
    }
    @RequestMapping(path = "/like" ,method = RequestMethod.POST)
    @ResponseBody
    String like(@RequestParam("commentId") int commentId){
        if(userHolder.getUser()==null){
            return GetJson.getJson(999);
        }
        User user=userHolder.getUser();
        String likekey= new KeysTool().getLikeCommentKey(EntityType.ENTITY_COMMENT,commentId);
        String dislikekey= new KeysTool().getdisLikeCommentKey(EntityType.ENTITY_COMMENT,commentId);
        if(redisAdapter.sismember(likekey,user.getId()+"")){
            long count=redisAdapter.scard(likekey);
         return GetJson.getJson(0,String.valueOf(count));
        }
        if(redisAdapter.sismember(dislikekey,user.getId()+"")){
            redisAdapter.srem(dislikekey,user.getId()+"");
        }
        try {
            redisAdapter.sadd(likekey,user.getId()+"");
        }catch (Exception e){
            logger.error("点赞失败！");
            return GetJson.getErroJson(1,"点赞失败！");
        }
        Event event=new Event();
        event.setAction_creater_id(userHolder.getUser().getId());
        event.setEntity_id(commentId);
        event.setEntity_owner(commentService.selectCommentbyid(commentId).getUserId());
        event.setEvent_type(EventType.LIKE);
        event.setEntity_type(EntityType.ENTITY_COMMENT);
        eventCreater.pushLikeCommentEvent(event);
        return GetJson.getJson(0,String.valueOf(redisAdapter.scard(likekey)));
    }

    @RequestMapping(path = "/dislike",method =RequestMethod.POST)
    @ResponseBody
    String dislike(@RequestParam("commentId") int commentId){
        if(userHolder.getUser()==null){
            return GetJson.getJson(999);
        }
        User user=userHolder.getUser();
        String likekey= new KeysTool().getLikeCommentKey(EntityType.ENTITY_COMMENT,commentId);
        String dislikekey= new KeysTool().getdisLikeCommentKey(EntityType.ENTITY_COMMENT,commentId);
        if(redisAdapter.sismember(dislikekey,user.getId()+"")){
            long count=redisAdapter.scard(likekey);
            return GetJson.getJson(0,String.valueOf(count));
        }
        if(redisAdapter.sismember(likekey,user.getId()+"")){
            redisAdapter.srem(likekey,user.getId()+"");
        }
        try {
            redisAdapter.sadd(dislikekey,user.getId()+"");
        }catch (Exception e){
            logger.error("点踩失败！");
            return GetJson.getErroJson(1,"点踩失败！");
        }
        return GetJson.getJson(0,String.valueOf(redisAdapter.scard(likekey)));
    }


}



