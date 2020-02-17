package com.example.practice.Controller;

import com.example.practice.Model.Question;
import com.example.practice.Model.UserHolder;
import com.example.practice.Service.QusestionService;
import com.example.practice.tools.GetJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class AddQuestionController {
    @Autowired
    QusestionService qusestionService;
    @Autowired
    UserHolder userHolder;

    @RequestMapping(path = "/question/add" ,method = RequestMethod.POST)
    @ResponseBody
    String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        Question question = new Question();
        GetJson getJson=new GetJson();
        question.setCreatedDate(new Date());
        question.setContent(content);
        question.setTitle(title);
        question.setUserId(userHolder.getUser().getId());
        question.setCommentCount(0);
        int code = 0;
        code = qusestionService.addquestion(question);
        if(code>0){
            return getJson.getJson(0);
        }
        return  getJson.getErroJson(1,"提交问题出错！");
    }

}
