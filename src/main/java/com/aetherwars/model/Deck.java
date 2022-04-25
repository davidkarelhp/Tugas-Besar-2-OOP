package com.aetherwars.model;

import com.aetherwars.model.cards.Card;

import java.util.*;

public class Deck {
    private Stack<Card> deck;

    public Deck() {
        this.deck = new Stack<>();
        // Harus dipikirin cara membentuk deck-nya pas awal-awal
        
        // ganti sama daftar card yg ada int[] initCard = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

//        ArrayList<Card> listCard = Arrays.asList(initCard);
//        ArrayList<Card> result = new ArrayList<Card>();
//        Random rand = new Random();
//        // random ukuran deck
//        int deckSize = rand.nextInt(61-40) + 40;
//        // random index daftar card untuk dimasukkan ke deck
//        while (result.size() < deckSize) {
//            int randIdx = rand.nextInt(listCard.size());
//            result.add(listCard.get(randIdx));
//        }
//        // memasukkan ke dalam deck
//        for (Card card : result) {
//            this.deck.push(card);
//        }
        
    }

    public Deck(List<? extends Card> cards) {
        this.deck = new Stack<>();
        for (Card card: cards) {
            this.deck.push(card);
        }

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

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < this.deck.size(); i++) {
            ret += this.deck.get(i).getName() + '\n';
        }
        return ret;
    }
}
