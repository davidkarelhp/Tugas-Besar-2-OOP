package com.aetherwars;

import com.aetherwars.model.Player;

public class Board {

    private Character[] Board;

    // Konstruktor
    public Board(){
        this.Board = new Character[5];
    }

    public void putCardInSlot(int slot, Character card){
        if (slot < 5 && slot > -1){
            this.Board[slot] = card;
        }
    }

    public Character[] getBoard(){
        return this.Board;
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
