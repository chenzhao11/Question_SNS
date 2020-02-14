package com.example.practice.Dao;

import com.example.practice.Model.Ticket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Component
@Repository
public interface TicketDao {

    String SELECT_FILED = "id,user_id,status,expired,ticket";
    String TABLE = "ticket";
    String INSERT_FILED = "user_id,status,expired,ticket";
    @Insert({"insert into ",TABLE," ( ",INSERT_FILED," ) value ( #{userId},#{status},#{expired},#{ticket} ) "})
    void add(Ticket ticket);
    @Update({"update ",TABLE," set status=#{status} where ticket=#{ticket} "})
    void update(@Param("status") int status,@Param("ticket") String ticket);
    @Select({"select ",SELECT_FILED," from ",TABLE," where ticket=#{ticket} "})
    Ticket seleteTicket(@Param("ticket") String ticket);
    @Select({"select user_id from ",TABLE," where ticket=#{ticket} "})
    int  seleteUserId(@Param("ticket") String ticket);
    @Select({"select status from ",TABLE," where ticket=#{ticket} "})
    int  seleteStatus(@Param("ticket") String ticket);
    @Select({"select expired from ",TABLE," where ticket=#{ticket} "})
    Date selectExpired(@Param("ticket") String ticket);
    @Select({"select ",SELECT_FILED," from ",TABLE," where user_id=#{id} "})
    List<Ticket> selectByUserId(@Param("id") int userid);
    @Update({"update ",TABLE," set expired=#{expired} where ticket=#{ticket} "})
    void updateExpired(@Param("expired") Date newtime,@Param("ticket") String ticket);
    @Select({"select ",SELECT_FILED," from ",TABLE," where ticket=#{ticket} "})
    List<Ticket> selectByTicket(@Param("ticket") String ticket);

}
