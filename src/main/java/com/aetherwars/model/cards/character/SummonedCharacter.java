package com.aetherwars.model.cards.character;

import com.aetherwars.model.Player;
import com.aetherwars.model.cards.spell.Spell;
import com.aetherwars.model.cards.spell.enums.SpellType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class SummonedCharacter implements IsSummoned {
    // Buat UI ini, abaiin aja
    private DoubleProperty bindedHealth;
    private DoubleProperty bindedAttack;
    private IntegerProperty bindedExp;
    private IntegerProperty bindedLevel;

    private Character character;
    private int level;
    private int exp;
    private double attack;
    private double health;

    // potions akan hilang untuk dua kondisi
    // jika durasi = 0
    // jika health = 0, dimana hanya berlaku untuk health yang positif saja
    private ArrayList<Spell> potions;
    private double attackSent;
    private double healthHad;
    private boolean playable;
    private int round;
    private int swapDurationLeft;
    public SummonedCharacter(Character character) {
        this.character = character;
        this.level = 1;
        this.exp = 0;
        this.attack = character.getBaseAttack();
        this.health = character.getBaseHealth();
        this.potions = new ArrayList<Spell>();
        this.attackSent = character.getBaseAttack();
        this.healthHad = character.getBaseHealth();
        this.playable = true;
        this.swapDurationLeft = 0;
        this.round = 0;

        this.bindedAttack = new SimpleDoubleProperty(character.getBaseAttack());
        this.bindedHealth = new SimpleDoubleProperty(character.getBaseHealth());
        this.bindedExp = new SimpleIntegerProperty(0);
        this.bindedLevel = new SimpleIntegerProperty(1);
    }

    public SummonedCharacter(Character character, int round, int level, int exp, double attack, double health, double mana, boolean playable) {
        this.character = character;
        this.level = level;
        this.exp = exp;
        this.attack = attack;
        this.health = health;
        this.potions = new ArrayList<>();
        this.attackSent = attack;
        this.healthHad = health;
        this.playable = playable;
        this.swapDurationLeft = 0;
        this.round = round;

        this.bindedAttack = new SimpleDoubleProperty(attack);
        this.bindedHealth = new SimpleDoubleProperty(health);
        this.bindedExp = new SimpleIntegerProperty(exp);
        this.bindedLevel = new SimpleIntegerProperty(level);
    }

    public double getBindedHealth() {
        return bindedHealth.get();
    }

    public DoubleProperty bindedHealthProperty() {
        return bindedHealth;
    }

    public double getBindedAttack() {
        return bindedAttack.get();
    }

    public DoubleProperty bindedAttackProperty() {
        return bindedAttack;
    }

    public int getBindedExp() {
        return bindedExp.get();
    }

    public IntegerProperty bindedExpProperty() {
        return bindedExp;
    }

    public int getBindedLevel() {
        return bindedLevel.get();
    }

    public IntegerProperty bindedLevelProperty() {
        return bindedLevel;
    }

    public int getRound() {
        return round;
    }

    public void incrementRound() {
        round++;
        potions.forEach((ts) -> ts.setDuration(ts.getDuration() - 1));
        swapDurationLeft--;

        // proses potions yang harus hilang karena durasi
        // duration = 0
        potions.removeIf((ts) -> ts.getDuration()  == 0);

        // normalisasi attackSent dan healthHad
        this.attackSent = this.attack;
        this.healthHad = this.health;

        // proses spell yang tersisa
        if (swapDurationLeft > 0) {
            double tmp = getHealthHad();
            setHealthHad(getAttackSent());
            setAttackSent(tmp);
        }

        potions.forEach((ts) -> {
            healthHad += ts.getHealth();
            attackSent += ts.getAttack();
        });

        this.setPlayable(true);
    }

    public Character getCharacter() {
        return character;
    }

    public ArrayList<Spell> getPotions() {
        return potions;
    }

    public void setPotions(ArrayList<Spell> potions) {
        this.potions = potions;
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
        this.bindedLevel.set(level);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        this.bindedExp.set(exp);
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.bindedAttack.set(Math.max(this.attackSent - this.attack + attack,0));
        this.attack = attack;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.bindedHealth.set(Math.max(this.healthHad - this.health + health, 0));
        this.health = health;
    }

    public double getAttackSent() {
        return attackSent;
    }

    public void setAttackSent(double attackSent) {
        this.attackSent = attackSent;
        this.bindedAttack.set(Math.max(this.attackSent,0));
    }

    public double getHealthHad() {
        return healthHad;
    }

    public void setHealthHad(double healthHad) {
        this.healthHad = healthHad;
        this.bindedHealth.set(Math.max(this.healthHad,0));
    }

    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public int getSwapDurationLeft() {
        return swapDurationLeft;
    }

    public void setSwapDurationLeft(int swapDurationLeft) {
        this.swapDurationLeft = swapDurationLeft;
    }

    public boolean isStronger(SummonedCharacter enemy) {
        return ((getType() == CharacterType.OVERWORLD && enemy.getType() == CharacterType.END) || (getType() == CharacterType.END && enemy.getType() == CharacterType.NETHER) || (getType() == CharacterType.NETHER && enemy.getType() == CharacterType.OVERWORLD));
    }

    public void attacked(SummonedCharacter attacker) {
        double damage = 0.0;
        if (isStronger(attacker)) {
            damage = 0.5 * attacker.getAttackSent();
            // add condition if attacker dies
        }
        else if (attacker.isStronger(this)) {
            damage = 2 * attacker.getAttackSent();
            // add condition if attacker dies
        }
        else if (getType() == attacker.getType()) {
            damage = attacker.getAttackSent();
            // add condition if attacker dies
        }

        double damageLeft = damage;

        int i = potions.size() - 1;
        while (damageLeft > 0 && i >= 0){
            Spell p = potions.get(i);
            if (p.getHealth() > 0) {
                damageLeft = Math.max(0, damageLeft - p.getHealth());
                p.setHealth(Math.max(p.getHealth() - damageLeft, 0));
            }
            i--;
        }

        setHealth(getHealth() - damageLeft);
    }
    
    public void attackEnemy(SummonedCharacter enemy) {
        enemy.attacked(this);
        this.setPlayable(false);
    }

    public void attackPlayer(Player p) {
        p.setHealthPoints((p.getHealthPoints() - this.attack < 0) ? 0 : p.getHealthPoints() - this.attack);
        this.setPlayable(false);
    }

    public void addSpell(Spell spell) {
        if (spell.getType() == SpellType.POTION) {
            potions.add(spell);
        }

        if (spell.getType() == SpellType.SWAP) {
            swapDurationLeft += spell.getDuration();
        }

        spell.runEffect(this);
        // saat potion punya health minus yang ngabisin dia sampe mati, itu harus diapain?
    }




    public void levelUp() {
        setAttack(getAttack() + getAttackUp());
        setHealth(getHealth() + getHealthUp());
        setLevel(getLevel() + 1);
    }

    public void levelDown() {
        setAttack(getAttack() - getAttackUp());
        setHealth(getHealth() - getHealthUp());
        setLevel(getLevel() - 1);
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