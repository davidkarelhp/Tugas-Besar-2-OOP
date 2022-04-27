package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.AttackModifier;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.characteristics.HealthModifier;
import com.aetherwars.model.cards.spell.characteristics.EffectRemover;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.ArrayList;

public class Potion implements AttackModifier, HealthModifier, EffectRunner {
    public final SpellType type = SpellType.POTION;
    public final EffectDurationType effectDurationType = EffectDurationType.TEMPORARY;
    public static ArrayList<Potion> PotionList = new ArrayList<>();

    private double attack;
    private double health;
    private int duration;

    public Potion(){
        this.attack = 0;
        this.health = 0;
        this.duration = 0;
    }

    public Potion(double attack, double health, int duration) {
        this.attack = attack;
        this.health = health;
        this.duration = duration;
    }

    public double getHealth(){
        return health;
    }

    public double getAttack() {
        return attack;
    }

    public int getDuration() {
        return duration;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setHealth(double health) {
        this.health = health;
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
    public void modifyAttack(SummonedCharacter character) {
        character.setAttackSent(character.getAttackSent() + attack);
    }

    @Override
    public void modifyHealth(SummonedCharacter character) {
       character.setHealthHad(character.getHealthHad() + health);
    }


}
