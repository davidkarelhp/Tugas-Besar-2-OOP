package com.aetherwars.event;

import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import javafx.util.Pair;

public class RefreshBoardEvent implements Event {
    Player player;
    Phase phase;

    public RefreshBoardEvent(Player player, Phase phase) {
        this.player = player;
        this.phase = phase;
    }

    @Override
    public Object getEvent() {
        return new Pair<Player, Phase>(this.player, this.phase);
    }
}
