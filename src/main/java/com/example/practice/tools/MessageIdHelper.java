package com.example.practice.tools;

/**
 * @Author: ZhaoChen
 * @Date: 2022/08/20/22:34
 * @Description:
 */
public class MessageIdHelper {
    public static Long getId(String UUID) {
        Integer userId = UUID.toString().hashCode();
        // String.hashCode()可能会是负数，如果为负数需要转换为正数
        userId = userId < 0 ? -userId : userId;
        return Long.valueOf(String.valueOf(userId));
    }

}
