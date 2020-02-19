package com.example.practice.Dao;

import com.example.practice.Model.Comment;
import com.example.practice.Model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " content, created_date, fromid,toid,conversation_id,has_read ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    //mybatis会自动完成时间类型的转换！
    @Insert({" insert into ", TABLE_NAME, " ( ", INSERT_FIELDS, " ) values ( " +
            " #{content},#{createdDate},#{fromid},#{toid},#{conversationId},#{hasRead})"})
    int insertMessage(Message message);

    /*按照conversationId找出一个对话的所有内容*/
    @Select({"select * from message where conversation_id=#{conversationId} order by created_date desc"})
    List<Message> selectMessage(@Param("conversationId") String conversationId);

    /*查询toid为userID  按照conversa_id分组组内按时间逆序   组间按时间逆序的*/
// select * , count(*) as num from (select * from message where toid=103 order by created_date desc )as tt group by conversation_id;


    //id当做有几条消息    消息列表     group只是返回组首一条
    @Select({" select ",INSERT_FIELDS ," , count(*) as id from (select * from message where " +
            "toid=#{toUserId} order by created_date desc )as tt  group by conversation_id order by has_read "})
    List<Message> selectMessageList (@Param("toUserId") int toUserId);
    @Select({" select  count(*)  from message where  conversation_id=#{conversationId} and toid=#{toUserId} and has_read=0"})
    int  getUnread(@Param("toUserId") int toUserId,@Param("conversationId") String conversationId);

    @Update("update message set has_read=#{hasReadStatus} where toid=#{toid} and conversation_id=#{conversationId}")
    void updateHasRead(@Param("toid") int toid,@Param("hasReadStatus") int hasReadStatus,@Param("conversationId") String conversationId);

}

