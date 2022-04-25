package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.AttackModifier;
import com.aetherwars.model.cards.spell.characteristics.HealthModifier;
import com.aetherwars.model.cards.spell.characteristics.TemporaryEffect;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

public class Potion extends Spell implements TemporaryEffect, AttackModifier, HealthModifier {
    private final double attack;
    private final double health;

    private double healthUsed;
    public Potion(double attack, double health){
        super(SpellType.POTION, EffectDurationType.TEMPORARY);
        this.attack = attack;
        this.health = health;
    }

    public double getDefaultAttack(){
        return attack;
    }

    public double getHealth(){
        return health - healthUsed;
    }

    public double getDefaultHealth() {
        return  health;
    }
//    @Override
//    public void summon() {
//
//    }

    @Override
    public void runEffect(SummonedCharacter character) {
        modifyHealth(character);
        modifyAttack(character);
    }

    @Override
    public void removeEffect(SummonedCharacter character) {

    }

    @Override
    public void modifyAttack(SummonedCharacter character) {
//        character.setAttack(character.getAttackUp() + attack); // should be attackUp
    }

    @Override
    public void modifyHealth(SummonedCharacter character) {
//        character.setHealth(character.getHealthUp() + health); // should be healthUp
    }


}
