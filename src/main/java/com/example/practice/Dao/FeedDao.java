package com.example.practice.Dao;

import com.example.practice.Model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FeedDao {
   String INSERT_FIELD="user_id,type,created_date,data";
    //拉模式，按照传入的list<userId>把List<feed>作为返回值
    List<Feed> pull (@Param("userIdList") List<Integer> userIdList,
    @Param("offset") int offset, @Param("count") int count);
    //添加feed
    @Insert({"insert into  feed  ( ",INSERT_FIELD,"  ) values ( #{userId},#{type},#{createdDate},#{data})"})
    int addFeed (Feed feed);




}

