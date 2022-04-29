package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.characteristics.ExperienceEater;
import com.aetherwars.model.cards.spell.characteristics.LevelModifier;
import com.aetherwars.model.cards.spell.characteristics.PermanentEffect;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.ArrayList;

public class LevelDown implements PermanentEffect, LevelModifier, ExperienceEater, EffectRunner {
    public static ArrayList<LevelDown> LevelDownList = new ArrayList<>();
    public final SpellType type = SpellType.LEVELDOWN;
    public final EffectDurationType effectDurationType = EffectDurationType.PERMANENT;
    public LevelDown() {
    }


    @Override
    public void runEffect(SummonedCharacter character) {
        modifyLevel(character);
    }

    @Override
    public void modifyLevel(SummonedCharacter character) {
        if (character.getLevel() - 1 >= 1) {
            eatExperience(character);
            character.levelDown();
            System.out.println("Level modified from " + (character.getLevel() + 1) + " to " + character.getLevel());
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
