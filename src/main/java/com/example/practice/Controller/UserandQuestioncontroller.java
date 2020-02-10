package com.example.practice.Controller;

import com.example.practice.Model.Question;
import com.example.practice.Model.User;
import com.example.practice.Service.QusestionService;
import com.example.practice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
public class UserandQuestioncontroller {
    @Autowired
    UserService userService;
    @Autowired
    QusestionService qusestionService;

    @RequestMapping("/adduser")
    @ResponseBody
    String adduser() {
        User user = new User();
        StringBuffer inf = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            user.setId(i);
            user.setName(String.format("user%d", i));
            user.setPassword(String.format("password%d", i));
            user.setSalt(UUID.randomUUID().toString());
            Random random = new Random();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            userService.insertuser(user);
            inf.append(user.toString() + "<br>");
        }

        return inf.toString();
    }

    @RequestMapping(path = "delete")
    @ResponseBody
    String delete() {
        userService.deleteuserbyid(1);
        userService.deleteuserbyid(2);

        return "ok";
    }


    @RequestMapping("/addquestion")
    @ResponseBody
    String addquestion(){
        Question question=new Question();
        StringBuffer inf=new StringBuffer();
        for(int i=10;i<60;i++){
            question.setTitle(String.format("title%d",i));
            question.setCommentCount(i-9);
            question.setCreatedDate(new Date());
            question.setUserId(i);
            question.setContent(String.format("这是来时用户id为%d的评论",i));
           qusestionService.addquestion(question);
           inf.append(question.toString()+"<br>");
            /*
           mysql的datetime问题
           Date date=new Date();
            question.setCreatedDate(date);
            */
           // SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM--DD,HH--MM--SS");
           // String datestr=dateFormat.format(new Date());

          /*  Date date=new Date();
            //gettime函数得到的是毫秒，mybatis自动完成时间的转化
            java.sql.Date sqldate=new java.sql.Date(date.getTime());
            question.setCreatedDate(sqldate);
            */
        }
        return inf.toString();
    }



    @RequestMapping(path = "/latest")
    @ResponseBody
    String lateset(){

        List<Question> questionList=qusestionService.latestquestion(0,20,30);
        StringBuffer stringBuffer=new StringBuffer();
        for (Question question:questionList
             ) {
            stringBuffer.append(question.toString()+"<br>");

        }
        return stringBuffer.toString();
    }




}
