package com.aetherwars.event;

public class DiscardToDrawEvent implements Event {
    private int idxDiscard;

    public DiscardToDrawEvent(int idxDiscard) {
        this.idxDiscard = idxDiscard;
    }

    @Override
    public Object getEvent() {
        return this.idxDiscard;
    }
}
