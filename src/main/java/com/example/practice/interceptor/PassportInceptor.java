package com.example.practice.interceptor;

import com.example.practice.Model.User;
import com.example.practice.Model.UserHolder;
import com.example.practice.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@Component
public class PassportInceptor implements HandlerInterceptor {
    @Autowired
    UserHolder userHolder;
    @Autowired
    TicketService ticketService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookieList = httpServletRequest.getCookies();
        User user=new User();
        if (cookieList != null) {
            for (Cookie cookie : cookieList) {
                if (cookie.getName().equals("ticket")) {

                    if(ticketService.getticketstatus(cookie.getValue())==1){
                        cookie.setMaxAge(0);
                        return true;
                    }
                    if(ticketService.getticketstatus(cookie.getValue())==0&&new Date().after(ticketService.getExpired(cookie.getValue()))){
                        return  true;
                    }
                    user=ticketService.getUser(cookie.getValue());
                    userHolder.setUser(ticketService.getUser(cookie.getValue()));
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && userHolder.getUser() != null) {
            modelAndView.addObject("user", userHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
       if(userHolder!=null)
       { userHolder.remove();}
    }
}
