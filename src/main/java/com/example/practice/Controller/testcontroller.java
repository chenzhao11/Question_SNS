package com.example.practice.Controller;

import com.example.practice.Dao.TicketDao;
import com.example.practice.Model.User;
import com.example.practice.Model.UserHolder;
import com.example.practice.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class testcontroller {
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketDao ticketDao;
    @Autowired
    UserHolder userHolder;

    @RequestMapping(path = "/testticket")
    @ResponseBody
    String getuserid(HttpServletRequest httpServletRequest) {
        Cookie[] cookieList = httpServletRequest.getCookies();
        User user = new User();
        if (cookieList != null) {
            for (Cookie cookie : cookieList) {
                if (cookie.getName().equals("ticket")) {
                    user = ticketService.getUser(cookie.getValue());
                    userHolder.setUser(ticketService.getUser(cookie.getValue()));
                    break;
                }
            }
        }
        return userHolder.getUser().toString();

    }

//    @RequestMapping(path = "/followers")
//    String flower(HttpServletRequest httpServletRequest) {
//       return "followers";
//
//    }
//    @RequestMapping(path = "/followees")
//    String flowee(HttpServletRequest httpServletRequest) {
//        return "followees";
//
//    }
//
//    @RequestMapping(path = "/profile")
//    String flow(HttpServletRequest httpServletRequest) {
//        return "profile";
//
//    }

}
