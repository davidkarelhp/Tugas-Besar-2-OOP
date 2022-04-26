package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.AttackModifier;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.characteristics.HealthModifier;
import com.aetherwars.model.cards.spell.characteristics.TemporaryEffect;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

public class Potion implements TemporaryEffect, AttackModifier, HealthModifier, EffectRunner {
    private double attack;
    private double health;
    private double healthUsed;

    private int duration;

    public Potion(){
        this.attack = 0;
        this.health = 0;
        this.duration = 0;
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

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setHealthUsed(double healthUsed) {
        this.healthUsed = healthUsed;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void runEffect(SummonedCharacter character) {
        modifyHealth(character);
        modifyAttack(character);
    }

    @Override
    public void removeEffect(SummonedCharacter character) {
        character.getPotionSpells().remove(this);
    }

    @Override
    public void modifyAttack(SummonedCharacter character) {
        character.setAttack(character.getAttack() + attack);
    }

    @Override
    public void modifyHealth(SummonedCharacter character) {
       character.setHealth(character.getHealth() + health);
    }


}
