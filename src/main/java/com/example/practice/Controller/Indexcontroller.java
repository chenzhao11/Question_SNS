package com.example.practice.Controller;

import com.example.practice.Service.IndexService;
import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.Enumeration;
import java.util.List;

@Controller

public class Indexcontroller {
    @Autowired
    IndexService indexservice;
   private static final Logger logger=LoggerFactory.getLogger(Indexcontroller.class);
//   @RequestMapping("showstring")
//    @ResponseBody
//    public String Showstring (){
//        return"hello world";
//    }
    /*
    index 默认不用加上后缀
     */
    @RequestMapping(path={"/"})
    public String Showpage(){
        return "index";
    }
    /**
     *
     * 获取URL里面的数据以及？后面传递过来的参数 默认是必须的若不加会产生404错误
     *
     * */
    @RequestMapping(path = "/help/{userid}/{name}")
    @ResponseBody
    public String testarray(@PathVariable("userid" ) String userid,@PathVariable("name") String name,
    @RequestParam(value = "phone" ,defaultValue = "123",required = false) String phone){
//        return userid+"<br>"+name+"<br>"+phone;
        String out=String.format("hello %s ,nice to meet you! your phone is %s",userid,phone);
        logger.info("jh");
        return out;


    }
   @RequestMapping("/index21")
    @ResponseBody
   /*
   httpresponse与httpservletresponse的区别！！！

    */
   /*
   * 只有session才可以当作参数传递，cookie要使用就直接使用     注解方式使用就ok
   *
   * */

    public String showstr(Model model, HttpServletRequest request, HttpServletResponse response,
                          HttpSession session,@CookieValue("JSESSIONID") String jessionid){
        model.addAttribute("name","zhaochen");
       Cookie name=new Cookie("name","zhaochn");
        response.addCookie(name);
        Enumeration<String> headerNames =request.getHeaderNames();
        StringBuffer stringBuffer=new StringBuffer();
        while (headerNames.hasMoreElements()){
            String headname=headerNames.nextElement();
            stringBuffer.append(headname+"|||"+"<br>");
        }
        return response.toString()+"<br>"+jessionid+"<br>"+session.toString()+stringBuffer;
   }







}
