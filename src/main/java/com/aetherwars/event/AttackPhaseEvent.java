package com.aetherwars.event;

import com.aetherwars.model.Hand;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.Board;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.Player;

import java.util.*;


public class AttackPhaseEvent implements Event {
    private Hand hand;
    private Card card;
    private Board board;
    private Character character;
    private Player player;
    private Player opponent;

    public AttackPhaseEvent(Hand hand, Card card, Board board, Character character, Player player, Player opponent) {
        this.hand = hand;
        this.card = card;
        this.board = board;
        this.character = character;
        this.player = player;
        this.opponent = opponent;
    }

    //public 

    @Override
    public Object getEvent(){
        return this.player;
    }
}
