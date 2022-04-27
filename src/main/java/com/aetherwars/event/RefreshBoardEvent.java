package com.aetherwars.event;

import com.aetherwars.model.Player;

public class RefreshBoardEvent implements Event {
    Player player;

    public RefreshBoardEvent(Player player) {
        this.player = player;
    }

    @Override
    public Object getEvent() {
        return this.player;
    }
}
