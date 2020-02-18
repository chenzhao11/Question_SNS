package com.example.practice.Dao;

import com.example.practice.Model.Comment;
import com.example.practice.Model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " content, created_date, fromid,toid,conversation_id,has_read ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    //mybatis会自动完成时间类型的转换！
    @Insert({" insert into ",TABLE_NAME," ( ",INSERT_FIELDS," ) values ( "+
            " #{content},#{createdDate},#{fromid},#{toid},#{conversationId},#{hasRead})"})
    int insertMessage (Message message);
    /*按照conversationId找出一个对话的所有内容*/
    @Select({"select * from message where conversation_id=#{conversationId} order by created_date desc"})
    List<Message> selectMessage(@Param("conversationId") String conversationId);


}
