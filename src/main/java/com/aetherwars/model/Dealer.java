package com.aetherwars.model;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.spell.Heal;
import com.aetherwars.model.cards.spell.LevelDown;
import com.aetherwars.model.cards.spell.LevelUp;
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
                Spell spellCardData = null;
                int spellType = ThreadLocalRandom.current().nextInt(0, 5);
                if (spellType == 1) {
                    int levelUpOrDown = ThreadLocalRandom.current().nextInt(0, 2);
                    if (levelUpOrDown == 0) {
                        spellCardData = new Spell(998, "Level Down", "Level down the character. Mana required is ceil rounding of target character level divided by two", -1, null, new LevelDown());
                    } else {
                        spellCardData = new Spell(998, "Level Up", "Level up the character. Mana required is ceil rounding of target character level divided by two", -1, null, new LevelUp());
                    }
                } else if (spellType == 2) {
                    int healAmount = ThreadLocalRandom.current().nextInt(0, 3);
                    if (healAmount == 0) {
                        spellCardData = new Spell(1000, "Healing", "Heal character permanently", 3, null, new Heal(5));
                    } else if (healAmount == 1) {
                        spellCardData = new Spell(1000, "Healing", "Heal character permanently", 2, null, new Heal(3));
                    } else {
                        spellCardData = new Spell(1000, "Healing", "Heal character permanently", 1, null, new Heal(1));
                    }
                }else {
                    int randomSpellCardId = ThreadLocalRandom.current().nextInt(0, Spell.SpellList.size());
                    spellCardData = Spell.SpellList.get(randomSpellCardId);
                }

                listDeck.add(spellCardData.clone());
            }

//            int randomSpellCardId = ThreadLocalRandom.current().nextInt(0, Spell.SpellList.size());
//            Spell spellCardData = Spell.SpellList.get(randomSpellCardId);
//
//            listDeck.add(spellCardData.clone());
        }

        return new Deck(listDeck);
    }
}
