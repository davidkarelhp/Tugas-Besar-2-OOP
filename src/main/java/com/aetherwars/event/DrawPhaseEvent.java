package com.aetherwars.event;

import com.aetherwars.model.cards.Card;

import java.util.List;

public class DrawPhaseEvent implements Event {

    private List<Card> cards;

    public DrawPhaseEvent(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public Object getEvent() {
        return this.cards;
    }
}
