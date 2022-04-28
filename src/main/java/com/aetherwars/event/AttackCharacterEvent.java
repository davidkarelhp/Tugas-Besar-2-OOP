package com.aetherwars.event;

public class AttackCharacterEvent implements Event {
    private int idxAttacker;
    private int idxDefender;

    public AttackCharacterEvent(int idxAttacker, int idxDefender) {
        this.idxAttacker = idxAttacker;
        this.idxDefender = idxDefender;
    }

    @Override
    public Object getEvent() {
        return new int[]{this.idxAttacker, this.idxDefender};
    }
}
