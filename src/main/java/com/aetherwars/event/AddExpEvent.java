package com.aetherwars.event;

public class AddExpEvent implements Event {
    private int mana;
    private int idxBoard;

    public AddExpEvent(int mana, int idxBoard) {
        this.mana = mana;
        this.idxBoard = idxBoard;
    }

    @Override
    public Object getEvent() {
        return new int[]{this.mana, this.idxBoard};
    }
}
