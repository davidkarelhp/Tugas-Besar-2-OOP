package com.aetherwars.event;

import com.aetherwars.model.Player;

public class ChangePlayerEvent implements Event {
    private Player player;

    public ChangePlayerEvent(Player player) {
        this.player = player;
    }

    @Override
    public Object getEvent() {
        return this.player;
    }
}
