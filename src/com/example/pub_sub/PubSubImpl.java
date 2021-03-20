package com.example.pub_sub;

import java.util.HashMap;
import java.util.concurrent.*;

public class PubSubImpl implements PubSub {
    private ConcurrentHashMap<String,Topic> topicMap = new ConcurrentHashMap<>();
    @Override
    public <T> void addTopic(String topic, Class<T> cls) {
        if(topicMap.containsKey(topic)){
            System.out.println("Topic already added "+topic);
            return;
        }
        Topic<T> topicObj = new Topic<>(topic,cls);
        topicMap.putIfAbsent(topic,topicObj);
    }

    @Override
    public <T> void addSubscriber(String topic, Consumer<T> consumer, Class<T> cls, ExecutorService executor) {
        Topic tObj = topicMap.get(topic);
        if(tObj == null){
            throw new IllegalArgumentException("Topic not created "+topic);
        }
        if(!cls.isAssignableFrom(tObj.getCls())){
            throw new IllegalArgumentException("Invalid cls type found "+cls+" actual "+tObj.getCls());
        }
        BlockingQueue blockingQueue = new LinkedBlockingQueue();
        tObj.addQueue(blockingQueue);
        SubscriberWorker worker  = new SubscriberWorker(consumer,blockingQueue,executor);
        worker.start();

    }

    @Override
    public <T> void publish(String topic,  T event) {

        Topic tObj = topicMap.get(topic);
        if(tObj == null){
            throw new IllegalArgumentException("Topic not created "+topic);
        }
        if(!event.getClass().isAssignableFrom(tObj.getCls())){
            throw new IllegalArgumentException("Invalid cls type found "+event.getClass()+" actual "+tObj.getCls());
        }
        MyEvent myEvent = new MyEvent(event,false);
        tObj.sendToQueues(event);
    }
}
