package com.aetherwars;

import com.aetherwars.model.Player;

public class Board {

    private com.aetherwars.model.cards.character.Character[] Board;

    // Konstruktor
    public Board(){
        this.Board = new com.aetherwars.model.cards.character.Character[5];
    }

    public void putCardInSlot(int slot, com.aetherwars.model.cards.character.Character card){
        if (slot < 5 && slot > -1){
            this.Board[slot] = card;
        }
    }

    public com.aetherwars.model.cards.character.Character getCharacter(int i){
        return Board[i];
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
