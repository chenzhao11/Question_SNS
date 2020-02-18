package com.example.practice.Dao;

import com.example.practice.Model.Comment;
import com.example.practice.Model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " content, created_date, user_id, status, entity_type, entity_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    //mybatis会自动完成时间类型的转换！
    @Insert({" insert into ",TABLE_NAME," ( ",INSERT_FIELDS," ) values ( "+
            " #{content},#{createdDate},#{userId},#{status},#{entityType},#{entityId})"})
    int insertComment (Comment comment);
    @Select({"select * from comment where entity_type=#{entityType} and entity_id=#{entityId}"})
    List<Comment> selectComment(@Param("entityType") int entityType, @Param("entityId") int entityId);
}
