package com.example.practice.tools;

import org.springframework.stereotype.Service;

@Service
public class KeysTool {
    private final String SPLIT = ":";
    private final String COMMENT = "comment";
    private final String FOLLOWER = "FOLLOWER";
    private final String FOLLOWEE = "FOLLOWEE";
    private final String ZUNIONSTORE = "ZUNIONSTORE";

    public String getLikeCommentKey(int entityType, int entityId) {
        return "LIKE " + COMMENT + SPLIT + entityType + "_" + entityId;
    }

    public String getdisLikeCommentKey(int entityType, int entityId) {
        return "DISLIKE " + COMMENT + SPLIT + entityType + "_" + entityId;
    }

    public String getAsyncEventKey() {
        return "ASYNCRONOUS_EVENT_KEY";
    }

    public String getFollowerKey(int entity_type, int entity_id) {

        return FOLLOWER + SPLIT + entity_type + "_" + entity_id;
    }

    public String getFolloweeKey(int entity_type, int userId) {

        return FOLLOWEE + SPLIT + entity_type + "_" + userId;
    }

    public String getZunionStoreKey(int entity_type1, int entity_id1, int entity_type2, int entity_id2) {

        return ZUNIONSTORE + SPLIT + entity_type1 + "_" + entity_id1+"_"+ entity_type2 + "_" + entity_id2;
    }

}
