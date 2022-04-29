package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.*;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Morph implements PermanentEffect, CharacterChanger, EffectRunner {

    public final SpellType type = SpellType.MORPH;
    public final EffectDurationType effectDurationType = EffectDurationType.PERMANENT;
    public static ArrayList<Morph> MorphList = new ArrayList<>();

    private int targetId;
    private Character targetCharacter;
    public Morph() {
        targetId = -1;
    }

    public Morph(int targetId) {
        this.targetId = targetId;
        targetCharacter = Character.characterList.stream().filter(element -> element.getId() == getTargetId()).collect(Collectors.toList()).get(0);
    }

    public Character getTargetCharacter() {
        return targetCharacter;
    }

    public void setTargetCharacter(Character targetCharacter) {
        this.targetCharacter = targetCharacter;
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
        character.setCharacter(targetCharacter);
        character.setAttackSent(character.getAttackSent() - character.getAttack() + targetCharacter.getBaseAttack());
        character.setHealthHad(character.getHealthHad() - character.getHealth() + targetCharacter.getBaseHealth());
        character.setAttack(targetCharacter.getBaseAttack());
        character.setHealth(targetCharacter.getBaseHealth());
        character.setExp(0);
        character.setLevel(1);
    }
}
