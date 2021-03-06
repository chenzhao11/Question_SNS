package com.example.practice.Service;

import com.example.practice.Dao.Userdao;
import com.example.practice.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@Component
public class UserService {
    @Autowired
    Userdao userdao;
    @Autowired
    SensitiveService sensitiveService;

    public User getuserbyid(int id) {
        return userdao.selectbyid(id);
    }

    public  void updateuserbyid(User user) {
        userdao.updateuserbyid(user);
    }

    public void deleteuserbyid(int id) {
        userdao.deletebyid(id);
    }
    public void insertuser(User user){
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        user.setName(sensitiveService.filter(user.getName()));
        userdao.insertuser(user);
    }
    public User getUserByName(String name){return  userdao.selectbyname(name);}
    public int getIdByName(String name){return  userdao.selectidbyname(name);}



}
