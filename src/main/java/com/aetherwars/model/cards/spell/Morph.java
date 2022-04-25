package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.CharacterChanger;
import com.aetherwars.model.cards.spell.characteristics.PermanentEffect;
import com.aetherwars.model.cards.spell.characteristics.Swapper;
import com.aetherwars.model.cards.spell.characteristics.TemporaryEffect;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

public class Morph extends Spell implements PermanentEffect, CharacterChanger {
    public Morph(){
        super(SpellType.MORPH, EffectDurationType.PERMANENT);
    }

//    @Override
//    public void summon() {
//
//    }

    @Override
    public void runEffect(SummonedCharacter character) {

    }


    @Override
    public void changeCharacter(SummonedCharacter character) {
        character = character; //supposedly other character
    }
}
