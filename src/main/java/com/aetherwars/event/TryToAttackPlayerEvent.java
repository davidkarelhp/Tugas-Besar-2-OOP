package com.aetherwars.event;

import com.aetherwars.model.Player;

public class TryToAttackPlayerEvent implements Event {
    int idx;

    public TryToAttackPlayerEvent(int idx) {
        this.idx = idx;
    }

    @Override
    public Object getEvent() {
        return this.idx;
    }
}
