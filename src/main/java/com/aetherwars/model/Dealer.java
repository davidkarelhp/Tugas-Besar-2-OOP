package com.aetherwars.model;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.spell.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dealer {
    public static Deck getRandomDeck(int deckSize) {
        List<Card> listDeck = new ArrayList<>();

        for (int i = 0; i < deckSize; i++) {
            int cardType = ThreadLocalRandom.current().nextInt(0, 2);

            if (cardType == 0) {    // character card
                int randomCharCardId = ThreadLocalRandom.current().nextInt(0, Character.characterList.size());
                Character charCardData = Character.characterList.get(randomCharCardId);

                listDeck.add(new Character(charCardData.getId(), charCardData.getName(), charCardData.getDescription(), 
                charCardData.getMana(), charCardData.getImagePath(), charCardData.getCharacterType(), charCardData.getBaseAttack(), 
                charCardData.getBaseHealth(), charCardData.getAttackUp(), charCardData.getHealthUp()));
            } 
            else if (cardType == 1) {   // spell card
                int randomSpellCardId = ThreadLocalRandom.current().nextInt(0, Spell.SpellList.size());
                Spell spellCardData = Spell.SpellList.get(randomSpellCardId);

                listDeck.add(spellCardData.clone());
            }
        }

        return new Deck(listDeck);
    }
}
