package com.example.practice.Controller;

import com.example.practice.Model.EntityType;
import com.example.practice.Model.Question;
import com.example.practice.Model.User;
import com.example.practice.Model.ViewObject;
import com.example.practice.Service.SearchService;
import com.example.practice.Service.UserService;
import com.example.practice.tools.KeysTool;
import com.example.practice.tools.RedisAdapter;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

@Controller
@Component
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    UserService userService;
    @Autowired
    RedisAdapter redisAdapter;
    @RequestMapping(path= "/search",method = {RequestMethod.POST,RequestMethod.GET})
    String search(Model model, @RequestParam(value = "key",required = false) String key) {
        model.addAttribute("value",key);
        List<Question> questionList=new ArrayList<>();
        List<ViewObject> viewObjectList=new ArrayList<>();
        if(key!=null&&!StringUtils.isBlank(key)){
           questionList= searchService.search(key,0,100,"<em style=\"color:#FF0000\">","</em>");

        }else{
           questionList= searchService.search("*",0,10,"","");
        }
        if(questionList.size()==0){
            return "nomatch";
        }
        for (Question question:questionList
             ) {
            User user=userService.getuserbyid(question.getUserId());
            String followerKey= new KeysTool().getFollowerKey(EntityType.ENTITY_QUESTION,question.getId());
            ViewObject viewObject=new ViewObject();
            viewObject.set("user",user);
            viewObject.set("question",question);

            int followCount=Integer.parseInt(Long.toString(redisAdapter.scard(followerKey)));
            viewObject.set("followCount",followCount);
            viewObjectList.add(viewObject);
        }

        model.addAttribute("vos",viewObjectList);
        return "newresult";
    }
}
