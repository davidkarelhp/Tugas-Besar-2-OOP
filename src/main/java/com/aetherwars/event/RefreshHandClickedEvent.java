package com.aetherwars.event;

import com.aetherwars.model.Player;
import javafx.util.Pair;

public class RefreshHandClickedEvent implements Event {
    private Player player;
    private int idxClicked;

    public RefreshHandClickedEvent(Player player, int idxClicked) {
        this.player = player;
        this.idxClicked = idxClicked;
    }

    @Override
    public Object getEvent() {
        return new Pair<Player, Integer>(this.player, this.idxClicked);
    }
}
