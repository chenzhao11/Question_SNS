package com.example.practice.Dao;

import com.example.practice.Model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Component
@Repository
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
     //mybatis会自动完成时间类型的转换！
    @Insert({" insert into ",TABLE_NAME," ( ",INSERT_FIELDS," ) values ( "+
    " #{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int insertQuestion (Question question);
    List<Question> selectLatestQuestions(@Param("userid") int userid, @Param("offset") int offset, @Param("limit") int limit);
    @Select({"select * from question where id=#{questionId}"})
    Question selectQuestion (int questionId);
    @Update({"update question set comment_count=comment_count+1 where id=#{questionId}"})
   void  updateCommentCount(int questionId);

}
