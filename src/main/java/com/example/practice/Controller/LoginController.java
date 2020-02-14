package com.example.practice.Controller;

import com.example.practice.Model.Ticket;
import com.example.practice.Model.User;
import com.example.practice.Service.RegloginService;
import com.example.practice.Service.TicketService;
import com.example.practice.Service.UserService;
import com.example.practice.tools.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller

public class LoginController {
    @Autowired
    RegloginService regloginService;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;

    @RequestMapping(path = "/login")
    String loginpage(Model model, @RequestParam(value = "account") String account, @RequestParam(value = "password") String password
            , @RequestParam(value = "rememberme") boolean rememberme, HttpServletResponse httpServletResponse,@RequestParam(value = "next" ,required = false) String next) {
        User user = userService.getUserByName(account);
        Map<String, Object> map = new HashMap<>();
        if (user == null) {
            map.put("msg", "该用户i不存在！");
            model.addAttribute("msg", map);
            return "login";
        }
        String databasepassword = MD5Util.getMD5(password + user.getSalt());
        if (databasepassword.equals(user.getPassword())) {
            /*
            看数据库中是否有该用户id对应的未过期有效的ticket有的话就发送这个ticket就行
             */

            Date nowDate = new Date();
            int userid = userService.getIdByName(account);
            List<Ticket> databaseTicketList = ticketService.getByUserId(userid);
            Ticket databaseTicket = new Ticket();
            if (databaseTicketList != null) {
                for (Ticket ticketmiddle : databaseTicketList
                ) {
                    if (ticketmiddle.getStatus() == 0) {
                        databaseTicket = ticketmiddle;
                    }
                }
            }
            if (databaseTicketList != null) {
                if (databaseTicket.getTicket() != null) {
                    ticketService.updateExpired(databaseTicket.getTicket());

                    Cookie cookie = new Cookie("ticket", databaseTicket.getTicket());
                    if (rememberme) {
                        cookie.setMaxAge(3600 * 24 * 30);
                    }
                    cookie.setPath("/");
                    httpServletResponse.addCookie(cookie);
                } else {
                    ticketService.logout(databaseTicket.getTicket());
                    /*默认是没有退出登录的就不可以进入登陆界面*/
                    Ticket ticket = new Ticket();
                    ticket.setUserId(user.getId());
                    ticket.setTicket(UUID.randomUUID().toString().replace("-", ""));
                    ticket.setStatus(0);
                    Date date = new Date();
                    date.setTime(date.getTime() + 3600 * 24 * 60 * 1000);
                    ticket.setExpired(date);
                    ticketService.addTicket(ticket);
                    Cookie cookie = new Cookie("ticket", ticket.getTicket());
                    if (rememberme) {
                        cookie.setMaxAge(3600 * 24 * 30);
                    }
                    cookie.setPath("/");
                    httpServletResponse.addCookie(cookie);
                }

            }
            if(StringUtils.isBlank(next)){
                return "redirect:/index";
            }
            return next;

        }
        map.put("msg", "密码错误！");
        model.addAttribute("msg", map);
        return "login";

    }


    @RequestMapping(path = "/reglogin")
    String regloginpage(@RequestParam(value="next",required= false) String next, Model model) {
        model.addAttribute("next", next);
        return "login";
    }

    @RequestMapping(path = "/logout")
    String logoutpage(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ticket")) {
                    ticketService.logout(cookie.getValue());
                    break;
                }
            }
        }
        return "redirect:/index";
    }

    @RequestMapping(path = "/reg")
    String reg(Model model, @RequestParam(value = "account", required = false) String
            account, @RequestParam(value = "password", required = false) String password,
               @RequestParam(value = "rememberme", required = false) Boolean rememberme, HttpServletResponse
                       httpServletResponse ,@RequestParam(value = "next" ,required = false) String next) {

        Map<String, Object> map = regloginService.reg(account, password);
        if (map.get("msg") != null) {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
        Ticket ticket = new Ticket();
        ticket.setUserId(Integer.parseInt(map.get("userId").toString()));
        ticket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        ticket.setStatus(0);
        Date date = new Date();
        date.setTime(date.getTime() + 3600 * 24 * 100 * 1000);
        ticket.setExpired(date);
        ticketService.addTicket(ticket);
        Cookie cookie = new Cookie("ticket", ticket.getTicket());
        cookie.setMaxAge(3600 * 24 * 100000);
        cookie.setPath("/");
        try {
            httpServletResponse.addCookie(cookie);
        } catch (Exception e) {
            System.out.println("erro happened" + e.getMessage() + e.getLocalizedMessage());
        }
        if(StringUtils.isBlank(next)){
            return "redirect:/index";
        }
        return next;
    }
}
