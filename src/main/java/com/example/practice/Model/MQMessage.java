package com.example.practice.Model;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/16:26
 * @Description:
 */
public class MQMessage {
    private int id;
    private String messageId;
    private String msg;
    private int sendTimes;
    private int version;
    private int status;
    public MQMessage(){

    }

    public MQMessage(int id, String messageId, String msg, int sendTimes, int version,
                     int status) {
        this.id = id;
        this.messageId = messageId;
        this.msg = msg;
        this.sendTimes = sendTimes;
        this.version = version;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public MQMessage setId(int id) {
        this.id = id;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public MQMessage setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MQMessage setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getSendTimes() {
        return sendTimes;
    }

    public MQMessage setSendTimes(int sendTimes) {
        this.sendTimes = sendTimes;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public MQMessage setVersion(int version) {
        this.version = version;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public MQMessage setStatus(int status) {
        this.status = status;
        return this;
    }
}
