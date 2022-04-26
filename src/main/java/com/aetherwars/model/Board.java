package com.aetherwars.model;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.SummonedCharacter;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<SummonedCharacter> board;

    // Konstruktor
    public Board(){
        this.board = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.board.add(null);
        }
    }

    public void putInSlot(int slot, SummonedCharacter c) {
        this.board.set(slot, c);
    }

    public SummonedCharacter getAtSlot(int idx) {
        return this.board.get(idx);
    }

    public void removeCardAtSlot(int idx) {
        this.board.set(idx, null);
    }

    public List<SummonedCharacter> getCharacterList(){
        return this.board;
    }

    public void attackCharacter(SummonedCharacter enemyChar){
        double EnemyHealth = enemyChar.getHealth();
        //nadia bantuin kerjain ya sebentar

    }

    public void attackFromSlot(int slot, Board enemyBoard, int enemySlot){
        if (slot <5 && slot > -1 && enemySlot < 5 && enemySlot > -1){
//            this.board.get(slot).attackEnemy(enemyBoard.Board[enemySlot]);
//            this.board.get(slot).receiveExperience();
        }
    }

    public void attackFromSlot(int slot, Player enemy){
//        if (slot <5 && slot > -1){
//            this.Board[slot].attackPlayer(enemy);
//            this.Board[slot].receiveExperience();
//        }
    }



}
