package com.aetherwars.event;

import javafx.util.Pair;

public class MoveToBoardEvent implements Event {
    private int idxHand;
    private int idxBoard;

    public MoveToBoardEvent(int idxHand, int idxBoard) {
        this.idxHand = idxHand;
        this.idxBoard = idxBoard;
    }

    @Override
    public Object getEvent() {
        return new Pair<>(this.idxHand, this.idxBoard);
    }
}
