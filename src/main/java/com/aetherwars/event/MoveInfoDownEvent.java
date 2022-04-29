package com.aetherwars.event;

public class MoveInfoDownEvent implements Event{
    private String info;

    public MoveInfoDownEvent(String info) {
        this.info = info;
    }

    @Override
    public Object getEvent() {
        return this.info;
    }
}
