package com.aetherwars.model.cards.character;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.spell.Spell;

import java.util.ArrayList;

public class SummonedCharacter {
    private Character character;
    private int level;
    private int exp;
    private double attack;
    private double health;
    private ArrayList<Spell> tempSpell;
    private boolean playable;

    public SummonedCharacter(Character character) {
        this.level = 1;
        this.exp = 0;
        this.attack = 0;
        this.health = 0;
        this.tempSpell = new ArrayList<Spell>();
        this.playable = true;
    }

    public SummonedCharacter(Character character, int level, int exp, double attack, double health, double mana, ArrayList<Spell> tempSpell, boolean playable) {
        this.character = character;
        this.level = level;
        this.exp = exp;
        this.attack = attack;
        this.health = health;
        this.tempSpell = tempSpell;
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

    public ArrayList<Spell> getTempSpell() {
        return tempSpell;
    }

    public void setTempSpell(ArrayList<Spell> tempSpell) {
        this.tempSpell = tempSpell;
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

    public void attackEnemy(SummonedCharacter enemy) {
        if (isStronger(enemy)) {
            double damage = 2 * getAttack();
            enemy.setHealth(enemy.getHealth() - damage);
            // add condition if enemy dies
        }
        else if (enemy.isStronger(this)) {
            double damage = 0.5 * getAttack();
            enemy.setHealth(enemy.getHealth() - damage);
            // add condition if enemy dies
        }
        else if (getType() == enemy.getType()) {
            enemy.setHealth(enemy.getHealth() - getAttack());
            // add condition if enemy dies
        }
    }

    public void addTempSpell(Spell spell) {
        this.tempSpell.add(spell);
    }

    public void useSpell(Spell spell) {
        spell.runEffect(this);
    }

    public void processSpellList() {
        tempSpell.forEach((spell -> spell.runEffect(this)));
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