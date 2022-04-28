package com.aetherwars.event;

import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import javafx.util.Pair;

public class RefreshBoardClickedEvent implements Event {
    private Player player;
    private Phase phase;
    private int idxClicked;

    public RefreshBoardClickedEvent(Player player, Phase phase, int idxClicked) {
        this.player = player;
        this.phase = phase;
        this.idxClicked = idxClicked;
    }

    @Override
    public Object getEvent() {
        return new Pair<Integer, Pair<Player, Phase>>(this.idxClicked, new Pair<Player, Phase>(this.player, this.phase));
    }
}
