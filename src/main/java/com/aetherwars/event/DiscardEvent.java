package com.aetherwars.event;

public class DiscardEvent implements Event {
    private int idx;

    public DiscardEvent(int idx) {
        this.idx = idx;
    }

    @Override
    public Object getEvent() {
        return this.idx;
    }
}
