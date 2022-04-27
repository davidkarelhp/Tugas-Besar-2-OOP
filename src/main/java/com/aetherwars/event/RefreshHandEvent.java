package com.aetherwars.event;

import com.aetherwars.model.Player;

public class RefreshHandEvent implements Event {

    private Player player;

    public RefreshHandEvent(Player player) {
        this.player = player;
    }

    @Override
    public Object getEvent() {
        return this.player;
    }
}
