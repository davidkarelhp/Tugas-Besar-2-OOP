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

    public List<SummonedCharacter> getListCharacter(){
        return this.board;
    }

    public void attackFromSlot(int slot, Board enemyBoard, int enemySlot){
        if (slot <5 && slot > -1 && enemySlot < 5 && enemySlot > -1){
<<<<<<< HEAD
            this.board.get(slot).attackEnemy(enemyBoard.board.get(enemySlot));
            //this.Board[slot].receiveExperience();
=======
//            this.board.get(slot).attackEnemy(enemyBoard.Board[enemySlot]);
//            this.board.get(slot).receiveExperience();
>>>>>>> 3db98d2226fd9a964998b65e37ea14a92d638c7f
        }
    }

    //attack direcltly Player Health
    public void attackEnemyHealth(int slot, Player enemy){
        int enemy_size = ((enemy.getBoard()).getListCharacter()).size();
        if (slot <5 && slot > -1 && enemy_size == 0){
            //attack with spell
        }   
    }



}
