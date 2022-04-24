package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.spell.characteristics.Swapper;
import com.aetherwars.model.cards.spell.characteristics.TemporaryEffect;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

public class Swap extends Spell implements TemporaryEffect, Swapper {
    public Swap(){
        super(SpellType.SWAP, EffectDurationType.TEMPORARY);
    }

//    @Override
//    public void summon() {
//
//    }

    @Override
    public void runEffect(Character character) {
        swap(character);
    }

    @Override
    public void removeEffect(Character character) {

    }

    @Override
    public void swap(Character character) {
//        double tmp = character.getHealth();
//        character.setHealth(character.getAttack());
//        character.setAttack(tmp);
        // LOGIC FOR STACKING RULES AFTER CREATION OF TEMPORARY CARD LIST CLASS
    }
}
