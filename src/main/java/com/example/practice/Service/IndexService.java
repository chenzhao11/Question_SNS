package com.example.practice.Service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class IndexService {
    public String  getnumber(){
        return "your phone number is 18782515406!";
    }
}
