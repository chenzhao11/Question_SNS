package com.example.practice.tools;

import com.example.practice.Model.Event;
import com.example.practice.common.MQMessage;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/20:41
 * @Description:
 */
public class ConvertMQMessageToEvent {
    public static Event convert(MQMessage message){
        Event event = new Event().setEvent_type(message.getEvent_type())
                .setAction_creater_id(message.getAction_creater_id())
                .setEntity_type(message.getEntity_type())
                .setEntity_id(message.getEntity_id())
                .setEntity_owner(message.getEntity_owner())
                .setMapAll(message.getMap());
        return event;
    }
}
