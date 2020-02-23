package com.example.practice.Async;

import com.example.practice.Model.Event;

import java.util.List;

public interface EventHandler {

    void work(Event event);
    List<EventType> getSupportEvent();
}
