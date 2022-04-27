package com.aetherwars.model;

import com.aetherwars.GameEngine;
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

    public SummonedCharacter selectedChar (int slot){
        return this.board.get(slot);
    }
    public void attackFromSlot(int slot, Board enemyBoard, int enemySlot){
        if (slot <5 && slot > -1 && enemySlot < 5 && enemySlot > -1){
            this.board.get(slot).attackEnemy(enemyBoard.selectedChar(enemySlot));
            //this.Board[slot].receiveExperience();
        }
    }

    //attack direcltly Player Health
    public void attackEnemyHealth(int slot, Player enemy){
        int enemy_size = ((enemy.getBoard()).getListCharacter()).size();
        if (slot <5 && slot > -1 && enemy_size == 0){
            //attack with spell
//            this.board.get(slot).processSpell();
            // WE GOTTA TALK ABOUT THIS
            double enemyHealth = enemy.getHealthPoints();
            enemyHealth = enemyHealth - this.selectedChar(slot).getAttack();
        }   
    }

    // update information of game round to every summoned character
    public void incrementRound(){
        board.forEach((sc) -> sc.incrementRound());
    }



}
