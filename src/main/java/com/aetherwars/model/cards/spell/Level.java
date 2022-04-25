package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.ExperienceEater;
import com.aetherwars.model.cards.spell.characteristics.LevelModifier;
import com.aetherwars.model.cards.spell.characteristics.PermanentEffect;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;
import com.aetherwars.model.cards.Card;

public class Level extends Spell implements PermanentEffect, LevelModifier, ExperienceEater {
    public Level(){
        super(SpellType.SWAP, EffectDurationType.PERMANENT);
    }

//    @Override
    public void summon() {

    }

    @Override
    public void runEffect(SummonedCharacter character) {
        modifyLevel(character);
    }

    @Override
    public void modifyLevel(SummonedCharacter character) {
        if (character.getLevel() - 1 >= 1){
            character.levelUp(); // supposedly levelDown
            eatExperience(character);
            System.out.println("Level modified from "+(character.getLevel() + 1)+ " to "+ character.getLevel());
        } else {
            System.out.println("Can't modify level, level constraints will be violated");
        }
    }

    @Override
    public void eatExperience(SummonedCharacter character) {
        character.setExp(0);
        System.out.println("Experience has been set to 0");
    }
}
