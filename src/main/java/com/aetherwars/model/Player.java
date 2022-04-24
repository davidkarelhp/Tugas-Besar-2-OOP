package com.aetherwars.model;

public class Player {
    private String playerName;
    private double HealthPoints;
    private int Mana;

    //ini nanti diganti sama list deck
    private int deck;
    private String hand_deck;
    private String board_deck;
    public Player() {

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
