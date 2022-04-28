package com.aetherwars.event;

import com.aetherwars.model.Player;
import javafx.util.Pair;

public class PrepareToAttackCharacterEvent implements Event {

    private Player player;
    private int idxAttacker;

    public PrepareToAttackCharacterEvent(Player player, int idxAttacker) {
        this.player = player;
        this.idxAttacker = idxAttacker;
    }

    @Override
    public Object getEvent() {
        return new Pair<Player, Integer>(this.player, this.idxAttacker);
    }
}
