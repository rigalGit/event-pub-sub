package com.example.pub_sub;

//Wrapper class on actual event for now only use is if we want to implement removeSubscriber then internally we can publish
// a special event with shouldStop true and just send to worker which we want to stop
public class MyEvent {
    private Object event;
    private boolean shouldStop;

    public MyEvent(Object event, boolean shouldStop) {
        this.event = event;
        this.shouldStop = shouldStop;
    }

    public Object getEvent() {
        return event;
    }

    public boolean isShouldStop() {
        return shouldStop;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "event=" + event +
                ", shouldStop=" + shouldStop +
                '}';
    }
}
