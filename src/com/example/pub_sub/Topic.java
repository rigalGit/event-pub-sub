package com.example.pub_sub;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Topic<T> {
    private String name;
    private List<BlockingQueue<MyEvent>> queueList;


    public Class<T> getCls() {
        return cls;
    }

    private Class<T> cls;

    public Topic(String name, Class<T> cls) {
        this.name = name;
        queueList = new ArrayList<>();
        this.cls = cls;
    }

    public synchronized void addQueue(BlockingQueue blockingQueue){
        queueList.add(blockingQueue);
    }

    public void sendToQueues(T t){
        System.out.println(     "sending to queues "+t);
        for (BlockingQueue<MyEvent> q : queueList) {
            System.out.println("sending event to queue ");
            q.offer(new MyEvent(t,false));
        }

//        queueList.forEach(q -> q.offer(new MyEvent(t,false)));
    }
}
