package com.aetherwars.model.cards.character;

import com.aetherwars.model.cards.character.Character;
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
    private double attackTemp;
    private double healthTemp;
    private boolean playable;

    public SummonedCharacter(Character character) {
        this.character = character;
        this.level = 1;
        this.exp = 0;
        this.attack = 0;
        this.health = 0;
        this.potionSpells = new ArrayList<Spell>();
        this.swapSpells = new ArrayList<Spell>();
        this.attackTemp = 0;
        this.healthTemp = 0;
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
        this.attackTemp = 0;
        this.healthTemp = 0;
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

    public double getAttackTemp() {
        return attackTemp;
    }

    public void setAttackTemp(double attackTemp) {
        this.attackTemp = attackTemp;
    }

    public double getHealthTemp() {
        return healthTemp;
    }

    public void setHealthTemp(double healthTemp) {
        this.healthTemp = healthTemp;
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

    public void usePotionHealth(Potion potion, Double damage) {
        double potionHealth = potion.getHealth();
        potion.setHealth(potionHealth - damage);
        damage -= potionHealth;
        if (potion.getHealth() <= 0) {
            potion.setHealth(0);
            potion.removeEffect(this);
        }
    }

    public void attackPotion(Double damage) {
        int n = potionSpells.size() - 1;
        while (damage != 0 || potionSpells.size() != 0) {
            Potion potion = ((Potion)potionSpells.get(n).getSpellWorker());
            usePotionHealth(potion, damage);
            n = potionSpells.size() - 1;
        }
    }

//    public void checkPotion() {
//        this.potionSpells.forEach(spell -> attackTemp += spell.getSpellWorker());
//    }
//
//    public double generateDamage() {
//
//    }

    public void attacked(SummonedCharacter attacker, double baseDamage) {
        if (isStronger(attacker)) {
            Double damage = new Double(0.5 * baseDamage);
            attackPotion(damage);
            setHealth(getHealth() - damage);
            // add condition if attacker dies
        }
        else if (attacker.isStronger(this)) {
            Double damage = new Double(2 * baseDamage);
            attackPotion(damage);
            setHealth(getHealth() - damage);
            // add condition if attacker dies
        }
        else if (getType() == attacker.getType()) {
            Double damage = new Double(getAttack());
            attackPotion(damage);
            setHealth(getHealth() - getAttack());
            // add condition if attacker dies
        }
    }
    
    public void attackEnemy(SummonedCharacter enemy) {
        if (isStronger(enemy)) {
            Double damage = new Double(2 * getAttack());
        }
        else if (enemy.isStronger(this)) {

        }
        else if (getType() == enemy.getType()) {

        }
        // enemy.attacked(this);
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