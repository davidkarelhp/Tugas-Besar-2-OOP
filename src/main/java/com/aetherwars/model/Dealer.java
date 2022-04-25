package com.aetherwars.model;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dealer {
    public static Deck getRandomDeck(int deckSize) {
        List<Character> listDeck = new ArrayList<>();
        for (int i = 0; i < deckSize; i++) {
            int randomCardId = ThreadLocalRandom.current().nextInt(0, Character.characterList.size());
            Character cardData = Character.characterList.get(randomCardId);
            listDeck.add(new Character(cardData.getId(), cardData.getName(), cardData.getDescription(), cardData.getMana(), cardData.getImagePath(), cardData.getCharacterType(), cardData.getBaseAttack(), cardData.getBaseHealth(), cardData.getAttackUp(), cardData.getHealthUp()));
        }
        return new Deck(listDeck);
    }
}
