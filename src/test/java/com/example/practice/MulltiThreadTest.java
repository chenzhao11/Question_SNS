package com.example.practice;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class MulltiThread extends Thread {
    private int code;

    MulltiThread(int code) {
        this.code = code;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

            System.out.println(String.format("Thread %d:  this is %d", code, i));
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.run();
    }
}

class Thread2 implements Runnable {

    @Override
    public void run() {

    }
}


public class MulltiThreadTest {
    public static void thread3() {
        for (int j = 0; j < 3; ++j) {
            final int id;
            id = j;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(1000);
                            System.out.println(String.format("%d:%d", id, i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }
    }

    static AtomicInteger lock = new AtomicInteger();

    public static void thread4() {
        for (int i = 0; i < 30; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 10; j++) {
                            int k = lock.incrementAndGet();
                            if (k == 300) {
                                System.out.println(k);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    static Integer total = 0;

    public static void thread5() {
        for (int i = 0; i < 3000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (total) {
                            for (int k = 0; k < 10; k++) {
                                ++total;
                                if(total==30000){
                                    System.out.println(total);
                                }

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testBlockingQueue() {
        BlockingQueue<String> blockingQueue=new ArrayBlockingQueue<>(2);
        int i=0;
        while(i<3){
            final int  finali=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        for(int k=0;k<6;k++){
                            blockingQueue.put(String.format("thread%d::  %d",finali,k));
                    }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }).start();
            i++;
        }
        int j=0;
        while(j<2){
            int finalJ = j;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{while (true){
                        String str="这是消费线程%d取出的：："+blockingQueue.take();
                        System.out.println(String.format(str, finalJ));
//                        System.out.println(Thread.currentThread().getName());
                    }
                       }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            j++;
        }
        Thread.dumpStack();
        System.out.println(Thread.activeCount());
        System.out.println(Thread.getAllStackTraces().toString());
    }
    public static void testExcutor(){
        ExecutorService executorService= Executors.newFixedThreadPool(3);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<10){
                    System.out.println(Thread.currentThread().getName()+String.format(";;;;%d",i));
                    ++i;
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<10){
                    System.out.println(Thread.currentThread().getName()+String.format(";;;;%d",i));
                    ++i;
                }
            }
        });
        executorService.shutdown();
        while(!executorService.isTerminated()){
            System.out.println("等待关闭！");
        }

    }
public static void testFuture(){
        ExecutorService executorService=  Executors.newSingleThreadExecutor();
    Future<Integer> future =executorService.submit(new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {

            return 1/0;
           // throw new IllegalArgumentException("参数错误");
        }
    });
try {
    executorService.shutdown();
    System.out.println(future.get());
    System.out.println(future.isDone());
}catch (Exception e){
    e.printStackTrace();

}


}
    public static void main(String args[]) {
//        for (int i = 0; i < 100; i++) {
//            MulltiThread test = new MulltiThread(i);
//            test.start();
//        }
       testFuture();


    }
}