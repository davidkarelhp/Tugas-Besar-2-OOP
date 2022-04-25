package com.aetherwars.model;

import com.aetherwars.event.GameChannel;

public class Player {
    private String playerName;
    private double healthPoints;
    private int mana;
    private Deck deck;
    private Hand hand;
//    private String hand_deck;
//    private String board_deck;
    private GameChannel channel;

    public Player(String playerName, Deck deck, GameChannel channel) {
        this.playerName = playerName;
        this.deck = deck;
        this.channel = channel;
        this.healthPoints = 80;
        this.mana = 1;
        this.hand = new Hand();
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public int getMana() {
        return mana;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
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
}
