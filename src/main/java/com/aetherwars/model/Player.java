package com.aetherwars.model;

import com.aetherwars.event.GameChannel;
import com.aetherwars.model.cards.Card;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class Player {
    private String playerName;
    private DoubleProperty healthPoints;
    private IntegerProperty mana;
    private int manaLimit;
    private Deck deck;
    private Hand hand;
//    private String hand_deck;
//    private String board_deck;
    private GameChannel channel;

    public Player(String playerName, Deck deck, GameChannel channel) {
        this.playerName = playerName;
        this.deck = deck;
        this.channel = channel;
        this.healthPoints = new SimpleDoubleProperty(80);
        this.mana = new SimpleIntegerProperty(1);
        this.manaLimit = 1;
        this.hand = new Hand();
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getHealthPoints() {
        return healthPoints.get();
    }

    public DoubleProperty healthPointsProperty() {
        return healthPoints;
    }

    public int getMana() {
        return mana.get();
    }

    public IntegerProperty manaProperty() {
        return mana;
    }

    public int getManaLimit() {
        return manaLimit;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
    }

    public void increaseManaLimit() {
        this.manaLimit = this.manaLimit < 10 ? this.manaLimit + 1 : this.manaLimit;
    }

    public void resetMana() {
        this.mana.set(this.manaLimit);
    }

    public void getCard(int deck){
        //ngambil dari deck
    }

    public void seeCard (String board_deck, String hand_deck){
        //ngelihat dulu deskripsinya
    }

    public void moveCard (String hand_deck, String board_deck){
        //pindahin dari list ini ke list yang satu lagi
    }

    public void turnPhase(){
        //pindah ke fase(?) ini sama kayak game state apa gimana?
    }

    public void drawOnly() {
        this.hand.putCard(this.deck.draw());
    }
}
