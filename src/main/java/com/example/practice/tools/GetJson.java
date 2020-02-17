package com.example.practice.tools;

import com.alibaba.fastjson.JSONObject;

public class GetJson {
   public static JSONObject jsonObject=new JSONObject();
    public static String getErroJson(int code,String msg){
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject.toJSONString();
    }
    public static String getJson(int code){
        jsonObject.put("code",code);
        return jsonObject.toJSONString();
    }
}
