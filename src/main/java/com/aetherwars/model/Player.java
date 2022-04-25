package com.aetherwars.model;

import com.aetherwars.event.GameChannel;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.Board;
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
    private Board board;
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
        this.board = new Board();
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

    public void getCard(Deck deck){
        
    }

    public void seeSpecificBoard(String CharacterName){
        Board CurrentBoard = this.board;
        for(int i = 0; i < 5; i++){
            Character currentCard = CurrentBoard.getCharacter(i);
            if(currentCard.getName() == CharacterName){
                currentCard.displayDesc();
            }
        }
    }

    public void seeAllCardBoard (){
        //menampilkan seluruh card yang ada di board
        Board CurrentBoard = this.board;
        for(int i = 0; i < 5; i++){
            Character currentCard = CurrentBoard.getCharacter(i);
            currentCard.displayDesc();
            
        }
    }

    public void moveCardToBoard (String CharacterName){
        
    }

    public void turnPhase(){
        //pindah ke fase(?) ini sama kayak game state apa gimana?
    }

    public void drawOnly() {
        this.hand.putCard(this.deck.draw());
    }
}
