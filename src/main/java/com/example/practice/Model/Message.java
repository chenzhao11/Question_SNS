package com.example.practice.Model;

import java.util.Date;

public class Message {

    private int id;
    private int fromid;
    private int toid;
    private String content;
    private String conversation_id;
    private Date createdDate;
    public  Message(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromid() {
        return fromid;
    }

    public void setFromid(int fromid) {
        this.fromid = fromid;
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromid=" + fromid +
                ", toid=" + toid +
                ", content='" + content + '\'' +
                ", conversation_id='" + conversation_id + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    public Message(int id, int fromid, int toid, String content, String conversation_id, Date createdDate) {
        this.id = id;
        this.fromid = fromid;
        this.toid = toid;
        this.content = content;
        this.conversation_id = conversation_id;
        this.createdDate = createdDate;
    }
/**
     *  `id` INT NOT NULL AUTO_INCREMENT,
     *   `fromid` INT NOT NULL,
     *   `toid` INT NOT NULL,
     *   `content` LONGTEXT NULL,
     *   `conversation_id` INT NOT NULL,
     *   `created_date` DATETIME NOT NULL,
     */






}
