package com.example.practice.Model;

import java.util.Date;

public class Message {

    private int id;
    private int fromid;
    private int toid;
    private String content;
    private String conversationId;
    private Date createdDate;
    private int hasRead;
    public  Message(){}

    public Message(int id, int fromid, int toid, String content, String conversationId, Date createdDate, int hasRead) {
        this.id = id;
        this.fromid = fromid;
        this.toid = toid;
        this.content = content;
        this.conversationId = conversationId;
        this.createdDate = createdDate;
        this.hasRead = hasRead;
    }

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

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }
}
