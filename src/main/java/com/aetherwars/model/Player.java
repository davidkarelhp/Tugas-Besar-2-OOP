package com.aetherwars.model;

import com.aetherwars.event.GameChannel;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import com.aetherwars.model.cards.character.SummonedCharacter;

import java.util.List;

public class Player {
    private String playerName;
    private DoubleProperty healthPoints;
    private IntegerProperty mana;
    private IntegerProperty manaLimit;
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
        this.mana = new SimpleIntegerProperty(100);
        this.manaLimit = new SimpleIntegerProperty(100);
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

    public void setMana(int mana) {
        this.mana.set(mana);
    }

    public int getManaLimit() {
        return manaLimit.get();
    }

    public IntegerProperty manaLimitProperty() {
        return manaLimit;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
    }

    public Board getBoard(){
        return board;
    }

    public void setHealthPoints(double healthPoints) {
        this.healthPoints.set(healthPoints);
    }

    public void increaseManaLimit() {
        this.manaLimit.set(this.getManaLimit() < 10 ? this.getManaLimit() + 1 : this.getManaLimit());
    }

    public void resetMana() {
        this.mana.set(this.getManaLimit());
    }

    public Card seeCardinHand(int index){
        Card card = this.hand.getCardAtIndex(index);
        return card;
    }

    //untuk melihat kartu pada Board
//    public Character seeSpecificBoard(int index){
//        Character currentCharacter = this.board.getCharacter(index);
//        return currentCharacter;

//    }

//    public void seeAllCardBoard (){
        //menampilkan deskripsi seluruh card yang ada di board
//        Board CurrentBoard = this.board;
//        for(int i = 0; i < 5; i++){
//            Character currentCard = CurrentBoard.getCharacter(i);
//            currentCard.displayDesc();
//
//        }
//    }

    public void moveCardToBoard (Character CharacterName, int ChooseSlot) {
//        this.board.putCardInSlot(ChooseSlot, CharacterName);
    }

    public void attackEnemyCharacter(Player Enemy, int slot, int enemySlot){
        Board enemyBoard = Enemy.getBoard();
        this.board.attackFromSlot(slot, enemyBoard, enemySlot);
    }

    public void attackEnemyPlayer(int slot, Player enemy){
        this.board.attackEnemyHealth(slot, enemy);
    }

    public void moveCardToBoard (SummonedCharacter CharacterName, int ChooseSlot){
        this.board.putInSlot(ChooseSlot, CharacterName);
    }

    public List<Card> draw() {
        return this.deck.draw();
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
