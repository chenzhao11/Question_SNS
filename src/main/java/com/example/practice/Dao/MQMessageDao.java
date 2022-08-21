package com.example.practice.Dao;

import com.example.practice.Model.MQMessage;
import com.example.practice.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/16:37
 * @Description:
 */
@Mapper
@Component
public interface MQMessageDao {
    String TABLE_NAME = "mqmessage";
    String INSERT_FIELDS = "message_id, msg, status, send_times, version";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({ " insert into ", TABLE_NAME, " ( ", INSERT_FIELDS, " ) values ( " +
                                                                 " #{messageId},#{msg},#{status},#{sendTimes},#{version})"})
    int insertMQMessage(MQMessage message);

    @Update("update mqmessage set status=#{status} where message_id=#{messageId}")
    void updateMessageStatus(@Param("status") int status,@Param("messageId") String messageId);

    @Select({ " select * from ", TABLE_NAME, " where  message_id=#{messageId}"})
    MQMessage getMQMessageByMessageId(String messageId);

    @Select({ " select version from ", TABLE_NAME, " where  message_id=#{messageId}"})
    int getVersionByMessageId(String messageId);

    @Select({ " select send_times from ", TABLE_NAME, " where  message_id=#{messageId}"})
    int getSendTimesByMessageId(String messageId);

    @Update("update mqmessage set send_times=#{sendTimes} where message_id=#{messageId}")
    void updateSendTimesByMessageId(@Param("messageId") String messageId,@Param("sendTimes") int sendTimes);


}
