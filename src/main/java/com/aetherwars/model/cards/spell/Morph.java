package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.*;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.stream.Collectors;

public class Morph implements PermanentEffect, CharacterChanger, EffectRunner {
    private int targetId;
    public Morph() {
        targetId = -1;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public void runEffect(SummonedCharacter character) {
        changeCharacter(character);
    }


    @Override
    public void changeCharacter(SummonedCharacter character) {
        if (targetId == -1){
            System.out.println("Target character have not been set");
            return;
        }
        character.setCharacter(Character.characterList.stream().filter(element -> element.getId() == getTargetId()).collect(Collectors.toList()).get(0));
        character.setExp(0);
        character.setLevel(1);
    }
}
