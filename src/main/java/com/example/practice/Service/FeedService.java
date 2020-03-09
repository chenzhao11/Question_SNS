package com.example.practice.Service;

import com.example.practice.Dao.FeedDao;
import com.example.practice.Model.EntityType;
import com.example.practice.Model.Feed;
import com.example.practice.Model.UserHolder;
import com.example.practice.tools.KeysTool;
import com.example.practice.tools.RedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Component
public class FeedService {
    @Autowired
    FeedDao feedDao;
    @Autowired
    UserHolder userHolder;
    @Autowired
    RedisAdapter redisAdapter;
    private  static  final Logger logger= LoggerFactory.getLogger(FeedService.class);
    public  List<Feed> pull(int offset,int count){
        if(userHolder.getUser()!=null){
            int localUserId=userHolder.getUser().getId();
            String followerkey=new KeysTool().getFolloweeKey(EntityType.ENTITY_USER,localUserId);
            Set<String> userIdSet=redisAdapter.zrang(followerkey,0,redisAdapter.zcard(followerkey).intValue());
            List<Integer> userIdList= parseToList(userIdSet);
            return feedDao.pull(userIdList,offset,count);
        }
        //没有登录就是全站的新鲜事
        List<Integer> userIdList=new ArrayList<>();
        return feedDao.pull(userIdList,offset,count);
    }
    public  boolean addFeed(Feed feed){
        try{
           int count= feedDao.addFeed(feed);
           if(count>0){
               return true;
           }
        }catch (Exception e){
            logger.error("添加feed流失败！");
        }
        return false;
    }

    List<Integer> parseToList(Set<String> set){
        List<Integer> result=new ArrayList<>();
        if(set!=null){
        for (String value:set
             ) {
            if(value!=""||value!=null){
            int middle=Integer.valueOf(value);
            result.add(middle);}
        }}
        return  result;
    }

}
