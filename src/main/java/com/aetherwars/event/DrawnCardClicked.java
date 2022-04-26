package com.aetherwars.event;

import com.aetherwars.model.cards.Card;
import javafx.util.Pair;

import java.util.List;

public class DrawnCardClicked implements Event {

    private List<Card> cards;
    private int idxCard;

    public DrawnCardClicked(List<Card> cards, int idxCard) {
        this.cards = cards;
        this.idxCard = idxCard;
    }

    @Override
    public Object getEvent() {
        return new Pair<>(this.cards, this.idxCard);
    }
}
