package com.aetherwars.event;

import com.aetherwars.model.Player;
import javafx.util.Pair;

public class PrepareToMoveToBoardEvent implements Event {
    private int idx;

    public PrepareToMoveToBoardEvent(int idx) {
        this.idx = idx;
    }

    @Override
    public Object getEvent() {
        return this.idx;
    }
}
