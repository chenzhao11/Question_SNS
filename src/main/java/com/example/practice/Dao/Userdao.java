package com.example.practice.Dao;

import com.example.practice.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Component
@Repository
public interface Userdao {
    String INSERT_COLUM = "name,password,salt,head_url";
    String TABLE = "user";
    String SELECT_COLUM = "id,name,password,salt,head_url";

    @Select({" select ", SELECT_COLUM, " from ", TABLE, " where  id=#{id}"})
    User selectbyid(int id);

    @Insert({" insert into ", TABLE, " ( ", INSERT_COLUM, " ) values " +
            " ( #{name},#{password},#{salt},#{headurl}) "})
    void insertuser(User user);

    @Update({" update ", TABLE, " set name=#{name},password=#{password} ," +
            "salt=#{salt},head_url=#{headurl} where id=#{id}"})
    void updateuserbyid(User user);

    @Delete({"delete from ", TABLE, " where id=#{id}"})
    void deletebyid(int id);

    @Select({" select ", SELECT_COLUM, " from ", TABLE, " where  name=#{name}"})
    User selectbyname(String name);

    @Select({" select id from ", TABLE, " where  name=#{name}"})
    int selectidbyname(String name);


}
