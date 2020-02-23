package com.example.practice.Model;

import com.example.practice.Async.EventType;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private EventType event_type;
    private int action_creater_id;
    private int entity_type;
    private int entity_id;
    private int entity_owner;
    private Map<String,Object> map=new HashMap<>();


    public EventType getEvent_type() {
        return event_type;
    }

    public Event setEvent_type(EventType event_type) {
        this.event_type = event_type;
        return this;
    }

    public int getAction_creater_id() {
        return action_creater_id;
    }

    public Event setAction_creater_id(int action_creater_id) {
        this.action_creater_id = action_creater_id;

        return this;
    }

    public int getEntity_type() {
        return entity_type;
    }

    public Event setEntity_type(int entity_type) {
        this.entity_type = entity_type;
        return this;
    }

    public int getEntity_id() {
        return entity_id;
    }

    public Event setEntity_id(int entity_id) {
        this.entity_id = entity_id;
        return this;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Event setMap(String key,String value) {
        map.put(key,value);
        return this;
    }

    public int getEntity_owner() {
        return entity_owner;
    }

    public Event setEntity_owner(int entity_owner) {
        this.entity_owner = entity_owner;
        return this;

    }
}
