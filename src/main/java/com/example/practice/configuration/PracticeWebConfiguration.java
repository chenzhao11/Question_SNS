package com.example.practice.configuration;
import com.example.practice.interceptor.PassportInceptor;
import com.example.practice.interceptor.test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Service
@Component
public class PracticeWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
     HandlerInterceptor passportInceptor;
    @Autowired
    HandlerInterceptor userMainPageInceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(passportInceptor);
        registry.addInterceptor(userMainPageInceptor).addPathPatterns("/usermainpage*");
        super.addInterceptors(registry);
    }
}
