package com.example.practice.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.practice.Model.*;
import com.example.practice.Service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    @Autowired
    UserHolder userHolder;
    @Autowired
    FeedService feedService;

    @RequestMapping(value = "/feed")
    String feed(Model model){
        //这里使用的默认的参数   可以传入翻页的offset以及count
       List<Feed> feedList= feedService.pull(0,10);
       //重点是在这里传入参数渲染，准备数据显示,handler数据已准备好
        List<ViewObject> viewObjectList=new ArrayList<>();
//        model.addAttribute("feeds",feedList);
        for (Feed feed:feedList
             ) {
            ViewObject viewObject=new ViewObject();
            viewObject.set("createdDate",feed.getCreatedDate());
            viewObject.set("question", JSONObject.parseObject(feed.get("question"), Question.class));
            viewObject.set("actUser",JSONObject.parseObject(feed.get("actUser"),User.class));
            viewObject.set("type",feed.getType());
            viewObjectList.add(viewObject);
        }
        model.addAttribute("feeds",viewObjectList);
        return "feeds";

    }
}
