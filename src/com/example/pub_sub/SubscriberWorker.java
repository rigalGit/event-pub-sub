package com.example.pub_sub;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SubscriberWorker  {

    private final Consumer consumer;
    private final BlockingQueue<MyEvent> blockingQueue;
    private final ExecutorService executorService;
    private volatile boolean shouldStop = false;

    public SubscriberWorker(Consumer consumer, BlockingQueue<MyEvent> blockingQueue, ExecutorService executorService) {
        this.consumer = consumer;
        this.blockingQueue = blockingQueue;
        this.executorService = executorService;
    }


    public void pollAndConsume()  {
        System.out.println("starting consumer ");
        while (true){
            System.out.println("polling queue consumer ");
            MyEvent event = null;
            try {
                event = blockingQueue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(event == null)
                continue;
            System.out.println("MyEvent "+event);
            if(event.isShouldStop()){
                break;
            }
            System.out.println("got event  "+event);
            consumer.consume(event.getEvent());

        }
    }

    public void start(){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pollAndConsume();
                }catch (Exception e){
                    System.out.println("errror"+e);
                    e.printStackTrace();
                }
            }
        });
    }


}
