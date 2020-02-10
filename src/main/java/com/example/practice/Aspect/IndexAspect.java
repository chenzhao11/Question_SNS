package com.example.practice.Aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class IndexAspect {
    private  static final Logger logger= LoggerFactory.getLogger(IndexAspect.class);

    /**
     * execution里面可以用*号但是层级结构必须表示出来
     */

    @Before("execution(* com.example.practice.*.*.*(..))")
    private void before(){
       // logger.info("方法使用之前被调用！");
    }
    @After("execution(* com.example.practice.*.*.*(..))")
    private void after(){
        //logger.info("   哈哈吧哈哈！方法使用之后被被调用！");
    }
}
