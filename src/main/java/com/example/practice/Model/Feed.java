package com.example.practice.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;

public class Feed {
    private int id;
    private int userId;
    private Date createdDate;
    private String data;
    private int type;
    //jsonobject内部就是一个map可以把json里面的键值对直接转换成一个map对象方便取值
    private JSONObject jsonObject=null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        jsonObject= JSON.parseObject(data);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
//方便后面的velocity到里面来取值，会默认调用get方法，所以直接在json串里面传入的数据也可以作为属性一样直接读取，使得bean变得灵活
    public String get(String key) {
        return jsonObject.getString(key);
    }
}
