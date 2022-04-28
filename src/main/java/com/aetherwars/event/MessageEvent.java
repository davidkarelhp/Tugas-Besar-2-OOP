package com.aetherwars.event;

public class MessageEvent implements Event{
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    @Override
    public Object getEvent() {
        return this.message;
    }
}
