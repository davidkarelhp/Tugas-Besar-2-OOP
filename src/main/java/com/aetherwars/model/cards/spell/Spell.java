package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.EffectRemover;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.ArrayList;

public class Spell extends Card implements EffectRunner, EffectRemover {
    public static ArrayList<Spell> SpellList = new ArrayList<>();
    private SpellType type;
    private EffectDurationType durationType;
    private double attack;
    private double health;
    private int targetId;
    private int duration;
    private EffectRunner worker;

    public Spell(int id, String name, String description, int mana, String imagePath, EffectRunner worker){
        super(id, name, description, mana, imagePath);
        this.worker = worker;
        attack = -1;
        health = -1;
        targetId = -1;
        duration = -1;

        if (worker instanceof LevelDown) {
            this.type = SpellType.LEVELDOWN;
        } else if (worker instanceof LevelUp){
            this.type = SpellType.LEVELUP;
        } else if (worker instanceof Potion){
            this.type = SpellType.POTION;
        } else if (worker instanceof Swap){
            this.type = SpellType.SWAP;
        } else if (worker instanceof Morph) {
            this.type = SpellType.MORPH;
        }

        // set the power
        if (worker instanceof Potion) {
            attack = ((Potion) worker).getAttack();
            health = ((Potion) worker).getHealth();
            duration = ((Potion) worker).getDuration();
        } else if (worker instanceof Swap) {
            duration = ((Swap) worker).getDuration();
        } else if (worker instanceof Morph) {
            targetId = ((Morph) worker).getTargetId();
        }
    }


    public void setType(SpellType type) {
        this.type = type;
    }

    public EffectDurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(EffectDurationType durationType) {
        this.durationType = durationType;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public EffectRunner getWorker() {
        return worker;
    }

    public void setWorker(EffectRunner worker) {
        this.worker = worker;
    }

    public SpellType getType(){
        return type;
    }


    public Spell clone(){
        Spell s = new Spell(getId(),getName(),getDescription(),getMana(),getImagePath(), getWorker());
        return s;
    }

    @Override
    public void displayDesc() {
        System.out.println("Hi i am a spell!");
    }

    @Override
    public void runEffect(SummonedCharacter character) {
        worker.runEffect(character);
    }

    @Override
    public void removeEffect(SummonedCharacter character) {
//        character.getTempSpell().remove(this);
    }
}
