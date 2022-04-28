package com.aetherwars.event;

import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import javafx.util.Pair;

public class RefreshHandEvent implements Event {

    private Player player;
    private Phase phase;

    public RefreshHandEvent(Player player, Phase phase) {
        this.player = player;
        this.phase = phase;
    }

    @Override
    public Object getEvent() {
        return new Pair<>(this.player, this.phase);
    }
}
