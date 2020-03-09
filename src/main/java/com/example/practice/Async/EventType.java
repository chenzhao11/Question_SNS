package com.example.practice.Async;

public enum  EventType {
    //中间是逗号，只有结束的地方才是分号
    LIKE(0),
    COMMENT(1),
    FOLLOW_USER(2),
    FOLLOW_QUESTION(3),
    ALERT(4);
    private int type;
    EventType( int type){
        this.type=type;
    }

   public  int getType(){
       return  type;
   }
}
