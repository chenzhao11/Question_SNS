package com.example.practice.Model;

import org.springframework.stereotype.Component;

@Component
public class UserHolder {
   private static ThreadLocal<User> user=new ThreadLocal<>();

    public User getUser() {
        return user.get() ;
    }

    public void setUser(User user) {
        this.user.set(user);
    }
    public void remove(){
        user.remove();
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
