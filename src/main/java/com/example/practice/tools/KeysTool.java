package com.example.practice.tools;

import org.springframework.stereotype.Service;

@Service
public class KeysTool {
    private final String SPLIT=":";
    private final String COMMENT="comment";

    public String getLikeCommentKey(int entityType,int entityId){
        return "LIKE "+COMMENT+SPLIT+entityType+"_"+entityId;
    }

    public String getdisLikeCommentKey(int entityType,int entityId){
        return "DISLIKE "+COMMENT+SPLIT+entityType+"_"+entityId;
    }
    public String getAsyncEventKey(){
        return "ASYNCRONOUS_EVENT_KEY";
    }


}
