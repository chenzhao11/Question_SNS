package com.example.practice.common;

import com.example.practice.Async.EventType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/17:38
 * @Description:
 */
public class MQMessage {

    private EventType          event_type;
    private int                action_creater_id;
    private int                entity_type;
    private int                entity_id;
    private int                entity_owner;
    private String messageId;
    private Map<String,Object> map =new HashMap<>();

    public MQMessage(){};
    public MQMessage(EventType event_type, int action_creater_id, int entity_type, int entity_id,
                     int entity_owner, String messageId, Map<String, Object> map) {
        this.event_type = event_type;
        this.action_creater_id = action_creater_id;
        this.entity_type = entity_type;
        this.entity_id = entity_id;
        this.entity_owner = entity_owner;
        this.messageId = messageId;
        this.map = map;
    }

    public MQMessage setEvent_type(EventType event_type) {
        this.event_type = event_type;
        return this;
    }

    public MQMessage setAction_creater_id(int action_creater_id) {
        this.action_creater_id = action_creater_id;
        return this;
    }

    public MQMessage setEntity_type(int entity_type) {
        this.entity_type = entity_type;
        return this;
    }

    public MQMessage setEntity_id(int entity_id) {
        this.entity_id = entity_id;
        return this;
    }

    public MQMessage setEntity_owner(int entity_owner) {
        this.entity_owner = entity_owner;
        return this;
    }

    public MQMessage setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MQMessage setMapValue(String key, Object val) {
        this.map.put(key, val);
        return this;
    }

    public EventType getEvent_type() {
        return event_type;
    }

    public int getAction_creater_id() {
        return action_creater_id;
    }

    public int getEntity_type() {
        return entity_type;
    }

    public int getEntity_id() {
        return entity_id;
    }

    public int getEntity_owner() {
        return entity_owner;
    }

    public String getMessageId() {
        return messageId;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
