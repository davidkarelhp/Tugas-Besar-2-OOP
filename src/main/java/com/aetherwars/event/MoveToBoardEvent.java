package com.aetherwars.event;

import com.aetherwars.model.Player;
import javafx.util.Pair;

public class MoveToBoardEvent implements Event {
    private Player player;
    private int[] idxHandBoard;
    private int idxHand;
    private int idxBoard;

    public MoveToBoardEvent(int[] idxHandBoard, Player player) {
        this.idxHandBoard = idxHandBoard;
        this.player = player;
    }

    public MoveToBoardEvent(int idxHand, int idxBoard) {
        this.idxHand = idxHand;
        this.idxBoard = idxBoard;
    }

    @Override
    public Object getEvent() {
        return new Pair<>(this.idxHandBoard, this.player);
    }
}
