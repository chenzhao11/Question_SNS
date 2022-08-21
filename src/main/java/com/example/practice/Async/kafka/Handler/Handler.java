package com.example.practice.Async.kafka.Handler;

import com.example.practice.common.MQMessage;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/21:50
 * @Description:
 */
public interface Handler {
    public void work(MQMessage message);
}
