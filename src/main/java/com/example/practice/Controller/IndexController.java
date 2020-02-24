package com.example.practice.Controller;

import com.example.practice.Model.Question;
import com.example.practice.Model.User;
import com.example.practice.Model.ViewObject;
import com.example.practice.Service.QusestionService;
import com.example.practice.Service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    List<ViewObject> getViewObjectList(int id, int offset, int limit) {
        List<Question> questionList = qusestionService.latestquestion(id, offset, limit);
        List<ViewObject> viewObjectList = new ArrayList<>();
        for (Question question : questionList
        ) {
            ViewObject viewObject = new ViewObject();
            User user = userService.getuserbyid(question.getUserId());
            viewObject.set("user", user);
            viewObject.set("question", question);
            viewObjectList.add(viewObject);

        }
        return viewObjectList;

    }

    @RequestMapping(path = {"/", "/index"})
    String indexpage(Model model) {
        List<ViewObject> objectList = getViewObjectList(0, 0, 10);
        model.addAttribute("viewObject", objectList);
        return "index";
    }

    @RequestMapping(path = "/profile")
    String authorlatest(@RequestParam("userId") int userid, Model model) {
//        List<ViewObject> objectList = getViewObjectList(userid, 0, 10);
//        model.addAttribute("viewObject", objectList);
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
        return "some erro hapens!" + "<br>" + e.getMessage() + "<br>" + e.toString();
    }


}



