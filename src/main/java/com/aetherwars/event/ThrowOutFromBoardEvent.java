package com.aetherwars.event;

public class ThrowOutFromBoardEvent implements Event {
    private int idx;

    public ThrowOutFromBoardEvent(int idx) {
        this.idx = idx;
    }

    @Override
    public Object getEvent() {
        return this.idx;
    }
}
