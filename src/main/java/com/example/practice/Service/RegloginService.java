package com.example.practice.Service;

import com.example.practice.Model.User;
import com.example.practice.Model.UserHolder;
import com.example.practice.tools.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
@Component
public class RegloginService {
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserHolder userHolder;
    public Map<String,Object> reg(String account,String password){
        Map<String,Object> map=new HashMap<>();
        //User userpanduan=userService.getUserByName(account);
        if(userService.getUserByName(account)!=null){
            map.put("msg","该用户名已被使用！");
            return map;
        }
        /*if(account.contains("admin")){
            map.put("msg","该用户名为敏感词！");
            return map;
        }*/
        User user=new User();
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        String salt=UUID.randomUUID().toString().replace("-","").substring(2,8);
        user.setSalt(salt);
        user.setName(account);
        user.setPassword(MD5Util.getMD5(password+salt));
        userService.insertuser(user);
        map.put("userId", userService.getIdByName(account));
        return map;


    }


}
