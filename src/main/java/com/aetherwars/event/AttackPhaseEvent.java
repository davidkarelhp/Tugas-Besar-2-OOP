package com.aetherwars.event;

import com.aetherwars.model.Hand;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.Board;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.Player;

import java.util.*;


public class AttackPhaseEvent implements Event {
    private Player player;

    public AttackPhaseEvent(Player player) {
        this.player = player;
    }

    //public 

    @Override
    public Object getEvent(){
        return this.player;
    }
}
