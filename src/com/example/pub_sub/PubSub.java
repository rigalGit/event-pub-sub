package com.example.pub_sub;

import java.util.concurrent.ExecutorService;

public interface PubSub {
    public <T> void addTopic(String topic, Class<T> cls);

    public <T> void addSubscriber(String topic, Consumer<T> consumer,Class<T> cls, ExecutorService executor);

    public <T> void publish(String topic, T event);


}
