package com.aetherwars.event;

import com.aetherwars.model.Player;

public class WinEvent implements Event {
    private Player winner;

    public WinEvent(Player winner) {
        this.winner = winner;
    }

    @Override
    public Object getEvent() {
        return this.winner;
    }
}
