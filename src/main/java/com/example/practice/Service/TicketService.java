package com.example.practice.Service;

import com.example.practice.Dao.TicketDao;
import com.example.practice.Dao.Userdao;
import com.example.practice.Model.Ticket;
import com.example.practice.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Component
public class TicketService {
    @Autowired
    TicketDao ticketDao;
    @Autowired
    Userdao userdao;

    public void addTicket(Ticket ticket) {
        ticketDao.add(ticket);
    }

    public User getUser(String ticket) {
        User user = new User();
        if(ticketDao.seleteStatus(ticket)==0){
        int id=ticketDao.seleteUserId(ticket);
        user = userdao.selectbyid(id);}
        return user;
    }

    public Ticket getTicket(String ticket) {
        return ticketDao.seleteTicket(ticket);
    }
    public void logout (String ticket){
        ticketDao.update(1,ticket);
    }
    public Integer getticketstatus (String ticket){
        return ticketDao.seleteStatus(ticket);
    }
    public Date getExpired(String ticket){
        return ticketDao.selectExpired(ticket);
    }
    public int getUserId(String ticket){
       return ticketDao.seleteUserId(ticket);
    }
    public List<Ticket> getByUserId(int id){
        return ticketDao.selectByUserId(id);
    }
    public List<Ticket> getByTicket(String ticket){
        return ticketDao.selectByTicket(ticket);
    }
    public void updateExpired(String ticket){
        Date newdate=new Date();
        newdate.setTime(newdate.getTime()+3600*24*1000*30);
        ticketDao.updateExpired(newdate,ticket);
    }

}
