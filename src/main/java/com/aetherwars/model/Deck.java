package com.aetherwars.model;

import com.aetherwars.model.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private Stack<Card> deck;

    public Deck() {
        this.deck = new Stack<>();
        // Harus dipikirin cara membentuk deck-nya pas awal-awal

    }

    public List<Card> draw() {
        List<Card> ret = new ArrayList<>();
        int  i = 0;
        while (i < 3 && !this.deck.isEmpty()) {
            ret.add(this.deck.pop());
            i++;
        }
        return ret;
    }

    public void putCard(List<Card> cards) {
        for (Card card: cards) {
            this.deck.push(card);
        }
    }

    public void shuffle() {
        Collections.shuffle(this.deck);
    }
}
