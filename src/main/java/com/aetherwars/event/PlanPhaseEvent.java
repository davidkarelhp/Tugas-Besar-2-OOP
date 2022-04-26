package com.aetherwars.event;

import com.aetherwars.model.Hand;
import com.aetherwars.model.cards.Card;

import java.util.List;

import com.aetherwars.model.Board;

public class PlanPhaseEvent implements Event {
    private Hand hand;
    private com.aetherwars.model.cards.character.Character character;
    private Board board;

    public PlanPhaseEvent(Hand hand, Board board, com.aetherwars.model.cards.character.Character charac ){
        this.hand = hand;
        this.board = board;
        this.character = charac;
    }

    @Override
    public Object getEvent(){
        return this.board;
    }

    public com.aetherwars.model.cards.character.Character getCharacter(){
        return this.character;
    }

    public Hand getHand(){
        return this.hand;
    }
}
