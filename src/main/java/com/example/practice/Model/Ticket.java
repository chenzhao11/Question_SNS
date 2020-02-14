package com.example.practice.Model;

import java.util.Date;

public class Ticket {
    /*
    CREATE TABLE `practice`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `status` INT NOT NULL,
  `expired` DATETIME NOT NULL,
  `ticket` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `ticket` (`ticket` ASC));
     */
    private int id;
    private int userId;
    private int status;
    private Date expired;
    private String ticket;

    public Ticket() {
    }

    public Ticket(int id, int userId, int status, Date expired, String ticket) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.expired = expired;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", expired=" + expired +
                ", ticket='" + ticket + '\'' +
                '}';
    }
}
