package com.example.practice.Async;

public enum  EventType {
    LIKE(0),
    COMMENT(1);

    private int type;
    EventType( int type){
        this.type=type;
    };

   public  int getType(){

       return  type;
   }
}
