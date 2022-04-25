package com.aetherwars.model;

import com.aetherwars.model.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
//    private List<Card> hand;
//
//    public Hand() {
//        this.hand = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            this.hand.add(null);
//        }
//    }
//
//    public Hand(List<Card> cards) {
//        // Konstruktor  hand pas awal, pas awal draw 3 kan
//        if (cards.size() != 3) {
//            throw new IllegalArgumentException("List kartu harus berukuran 3.");
//        }
//
//        this.hand = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            this.hand.add(null);
//        }
//        for (int i = 0; i < 3; i++) {
//            this.hand.set(i, cards.get(i));
//        }
//    }
//
//    public Card getCardAtIndex(int idx) {
//        // Kalau null berarti kosong
//        return this.hand.get(idx);
//    }
//
//    public void putCardAtIndex(int idx, Card card) {
//        // Kalau mau masukin di indeks tertentu, indeks itu harus kosong
//        if (!this.getCardAtIndex(idx).equals(null)) {
//            throw new IllegalArgumentException("Ada kartu lain pada indeks ini.");
//        }
//        this.hand.set(idx, card);
//    }
//
//    public void putCard(Card card) {
//        // Kalau mau masukkin di indeks terawal yang masih kosong
//        boolean found = false;
//        for (int i = 0; i < 5; i++) {
//            if (this.getCardAtIndex(i).equals(null)) {
//                this.putCardAtIndex(i, card);
//                found = true;
//                break;
//            }
//        }
//
//        if (!found) {
//            throw new IllegalArgumentException("Tidak ada tempat untuk memasukkan kartu.");
//        }
//    }
//
//    public void discardAtIndex(int idx) {
//        // Kalau mau discard kartu pada indeks tertentu
//        this.hand.set(idx, null);
//    }
}
