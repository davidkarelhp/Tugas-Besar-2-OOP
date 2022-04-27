package com.aetherwars.event;

import com.aetherwars.model.Player;
import javafx.util.Pair;

public class PrepareToMoveToBoardEvent implements Event {
    private Player player;
    private int idx;

    public PrepareToMoveToBoardEvent(Player player, int idx) {
        this.player = player;
        this.idx = idx;
    }

    @Override
    public Object getEvent() {
        return new Pair<>(this.player, this.idx);
    }
}
