package com.example.practice.Model;

import org.hibernate.validator.internal.util.CollectionHelper;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    private Map<String,Object> objectMap= CollectionHelper.newHashMap();

    public Object get(String key){
        return objectMap.get(key);
    }
    public void set(String key,Object object){
        objectMap.put(key, object);
    }





}
