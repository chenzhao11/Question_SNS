package com.example.practice.Model;

public class User {
    private int id;
    private String name;
    private String salt;
    private String headurl;
    private String password;
    public User(){}
    public User(int id, String name, String salt, String headurl, String password) {
        this.id = id;
        this.name = name;
        this.salt = salt;
        this.headurl = headurl;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headurl;
    }

    public void setHeadUrl(String headUrl) {
        this.headurl = headUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salt='" + salt + '\'' +
                ", headUrl='" + headurl + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
