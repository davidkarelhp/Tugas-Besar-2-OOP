package com.aetherwars.model.cards.character;

import com.aetherwars.model.cards.spell.Potion;
import com.aetherwars.model.cards.spell.Spell;

import java.util.ArrayList;

public class SummonedCharacter implements IsSummoned {
    private Character character;
    private int level;
    private int exp;
    private double attack;
    private double health;
    private ArrayList<Spell> potionSpells;
    private ArrayList<Spell> swapSpells;
    private double attackSent;
    private double healthHad;
    private boolean playable;

    public SummonedCharacter(Character character) {
        this.character = character;
        this.level = 1;
        this.exp = 0;
        this.attack = 0;
        this.health = 0;
        this.potionSpells = new ArrayList<Spell>();
        this.swapSpells = new ArrayList<Spell>();
        this.attackSent = 0;
        this.healthHad = 0;
        this.playable = true;
    }

    public SummonedCharacter(Character character, int level, int exp, double attack, double health, double mana, ArrayList<Spell> potionSpells, ArrayList<Spell> swapSpells, boolean playable) {
        this.character = character;
        this.level = level;
        this.exp = exp;
        this.attack = attack;
        this.health = health;
        this.potionSpells = potionSpells;
        this.swapSpells = swapSpells;
        this.attackSent = 0;
        this.healthHad = 0;
        this.playable = playable;
    }

    public CharacterType getType() {
        return character.getCharacterType();
    }

    public double getBaseAttack() {
        return character.getBaseAttack();
    }

    public double getBaseHealth() {
        return character.getBaseHealth();
    }

    public double getAttackUp() {
        return character.getAttackUp();
    }

    public double getHealthUp() {
        return character.getHealthUp();
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
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

    public ArrayList<Spell> getPotionSpells() {
        return potionSpells;
    }

    public void setPotionSpells(ArrayList<Spell> potionSpells) {
        this.potionSpells = potionSpells;
    }

    public ArrayList<Spell> getSwapSpells() {
        return swapSpells;
    }

    public void setSwapSpells(ArrayList<Spell> swapSpells) {
        this.swapSpells = swapSpells;
    }

    public double getAttackSent() {
        return attackSent;
    }

    public void setAttackSent(double attackSent) {
        this.attackSent = attackSent;
    }

    public double getHealthHad() {
        return healthHad;
    }

    public void setHealthHad(double healthHad) {
        this.healthHad = healthHad;
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public boolean isStronger(SummonedCharacter enemy) {
        return ((getType() == CharacterType.OVERWORLD && enemy.getType() == CharacterType.END) || (getType() == CharacterType.END && enemy.getType() == CharacterType.NETHER) || (getType() == CharacterType.NETHER && enemy.getType() == CharacterType.OVERWORLD));
    }

    // SKEMA
    // Skema attack
    // 1. Panggil processSpell
    // 2. Panggil enemy.attacked(attacker)
    // 3.

    public void processSpell() {

    }

    public void attacked(SummonedCharacter attacker) {
        processSpell();
        Double damage = 0.0;
        if (isStronger(attacker)) {
            damage = new Double(0.5 * attacker.getAttackSent());
            // add condition if attacker dies
        }
        else if (attacker.isStronger(this)) {
            damage = new Double(2 * attacker.getAttackSent());
            // add condition if attacker dies
        }
        else if (getType() == attacker.getType()) {
            damage = new Double(attacker.getAttackSent());
            // add condition if attacker dies
        }
        setHealth(getHealthHad() - damage);
    }
    
    public void attackEnemy(SummonedCharacter enemy) {
        processSpell();
        enemy.attacked(this);
    }

    public void useSpell(Spell spell) {
       spell.runEffect(this);
    }

    public void addPotionSpells(Spell spell) {
        this.potionSpells.add(spell);
    }
 
    public void processPotionSpellsList() {
       potionSpells.forEach((spell -> spell.runEffect(this)));
    }

    public void addSwapSpells(Spell spell) {
        this.swapSpells.add(spell);
    }

    public void processSwapSpellsList() {
       swapSpells.forEach((spell -> spell.runEffect(this)));
    }

    public void levelUp() {
        setAttack(getAttack() + getAttackUp());
        setHealth(getHealth() + getHealthUp());
        setLevel(getLevel() + 1);
    }

    public void addExp(int exp) {
        while (getExp() + exp >= 2 * getLevel() - 1) {
            exp -= (2 * getLevel() - 1);
            levelUp();
        }
        setExp(getExp() + exp);
    }

    @Override
    public String toString() {
        return "Name: " + character.getName() + "\nDescription: " + character.getDescription() + "\nType: " + getType() + "\nLevel: " + getLevel() + "\nExp: " + getExp();
    }

}