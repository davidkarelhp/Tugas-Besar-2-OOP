package com.aetherwars.model;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.SummonedCharacter;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private com.aetherwars.model.cards.character.Character[] Board;

    private List<SummonedCharacter> board; // Pake list aja kayaknya, lebih bagus kan?, terus Summoned character kan?

    // Konstruktor
    public Board(){
        this.board = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.board.add(null);
        }

        this.Board = new com.aetherwars.model.cards.character.Character[5];
    }

    public void putCardInSlot(int slot, com.aetherwars.model.cards.character.Character card){
        if (slot < 5 && slot > -1){
            this.Board[slot] = card;
        }
    }

    public void putInSlot(int slot, SummonedCharacter c) {
        this.board.set(slot, c);
    }

    public com.aetherwars.model.cards.character.Character getCharacter(int i){
        return Board[i];
    }

    public SummonedCharacter getAtSlot(int idx) {
        return this.board.get(idx);
    }

    public void removeCardAtSlot(int idx) {
        this.board.set(idx, null);
    }

    public void attackFromSlot(int slot, Board enemyBoard, int enemySlot){
//        if (slot <5 && slot > -1 && enemySlot < 5 && enemySlot > -1){
//            this.Board[slot].attackEnemy(enemyBoard.Board[enemySlot]);
//            this.Board[slot].receiveExperience();
//        }
    }

    public void attackFromSlot(int slot, Player enemy){
//        if (slot <5 && slot > -1){
//            this.Board[slot].attackPlayer(enemy);
//            this.Board[slot].receiveExperience();
//        }
    }



}
