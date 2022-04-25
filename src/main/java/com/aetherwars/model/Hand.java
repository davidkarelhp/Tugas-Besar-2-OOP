package com.aetherwars.model;

import com.aetherwars.model.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> hand;

    public Hand() {
        this.hand = new ArrayList<>();
    }

    public Hand(List<Card> cards) {
        // Konstruktor  hand pas awal, pas awal draw 3 kan
        if (cards.size() != 3) {
            throw new IllegalArgumentException("List kartu harus berukuran 3.");
        }

        this.hand = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            this.hand.add(cards.get(i));
        }
    }

    public Card getCardAtIndex(int idx) {
        // Kalau null berarti kosong
        return idx <= this.hand.size() - 1 ? this.hand.get(idx) : null;
    }


    public void putCard(Card card) {
        if (this.hand.size() >= 5) {
            throw new IllegalArgumentException("Tidak ada tempat untuk memasukkan kartu.");
        }

        this.hand.add(card);
    }

    public void putCard(List<Card> cards) {
//        if (this.hand.size() + cards.size() > 5) {
//            throw new IllegalArgumentException("Tidak ada tempat untuk memasukkan kartu.");
//        }

        this.hand.addAll(cards);
    }

    public void discardAtIndex(int idx) {
        // Kalau mau discard kartu pada indeks tertentu
        this.hand.remove(idx);
    }

    public List<Card> getHand() {
        return hand;
    }
}
