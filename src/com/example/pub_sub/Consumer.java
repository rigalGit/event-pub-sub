package com.example.pub_sub;

public interface Consumer<T> {
    void consume(T t);
}
