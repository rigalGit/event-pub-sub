package com.example.pub_sub;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        PubSubImpl pubSub = new PubSubImpl();


        pubSub.addTopic("str",String.class);
        pubSub.addTopic("int",Integer.class);


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        pubSub.addSubscriber("str",getStringConsumer("str1"),String.class,executorService);
        pubSub.addSubscriber("str",getStringConsumer("str2"),String.class,executorService);


//        pubSub.addSubscriber("int",getIntCOnsumer("str1"),Integer.class,executorService);
//        pubSub.addSubscriber("int",getIntCOnsumer("str2"),Integer.class,executorService);

        pubSub.publish("str","abc");

//        pubSub.publish("int",10);
//        pubSub.publish("int",20);
//        pubSub.publish("str","def");


        Thread.sleep(50*1000);


    }





    private static Consumer<String> getStringConsumer(String name){
        return new Consumer<String>() {
            @Override
            public void consume(String s) {
                System.out.println("start str consuming "+name+ " event " +s+new Date());
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end str consuming "+name+ " event " +s+new Date());
            }
        };
    }

    private static Consumer<Integer> getIntCOnsumer(String name){
        return new Consumer<Integer>() {
            @Override
            public void consume(Integer s) {
                System.out.println("start int consuming "+name+ " event " +s+new Date());
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end int consuming "+name+ " event " +s+new Date());
            }
        };
    }
}
