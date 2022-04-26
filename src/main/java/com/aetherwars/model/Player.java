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

<<<<<<< HEAD
    //untuk melihat kartu di tangan
    public Card seeCardinHand(int index){
=======
    public void increaseManaLimit() {
        this.manaLimit = this.manaLimit < 10 ? this.manaLimit + 1 : this.manaLimit;
    }

    public void resetMana() {
        this.mana.set(this.manaLimit);
    }

    public void seeCardinHand(int index){
>>>>>>> d1444cbe3932a213c3add11068e74f26a51d7092
        Card card = this.hand.getCardAtIndex(index);
        return card;
    }

    //untuk melihat kartu pada Board
    public Character seeSpecificBoard(int index){
        Character currentCharacter = this.board.getCharacter(index);
        return currentCharacter;

    }

    public void seeAllCardBoard (){
        //menampilkan deskripsi seluruh card yang ada di board
        Board CurrentBoard = this.board;
        for(int i = 0; i < 5; i++){
            Character currentCard = CurrentBoard.getCharacter(i);
            currentCard.displayDesc();
            
        }
    }

    public void moveCardToBoard (Character CharacterName, int ChooseSlot){
        this.board.putCardInSlot(ChooseSlot, CharacterName);
    }

<<<<<<< HEAD
    public void attackEnemy(Player enemy){

=======
    public List<Card> draw() {
        return this.deck.draw();
>>>>>>> d1444cbe3932a213c3add11068e74f26a51d7092
    }

    public void drawOnly() {
        this.hand.putCard(this.deck.draw());
    }

    public void putCardToDeckAndShuffle(List<Card> cards) {
        this.deck.putCard(cards);
        this.deck.shuffle();
    }

    public void addToHand(Card card) {
        this.hand.putCard(card);
    }
}
