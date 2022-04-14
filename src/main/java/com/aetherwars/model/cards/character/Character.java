package com.aetherwars.model.cards.character;

import com.aetherwars.model.cards.Card;

public class Character implements Card {
  private String name;
  private String description;
  private CharacterType characterType;
  private int level;
  private int exp;
  private double attack;
  private double health;
  private double mana;
  private double attackUp;
  private double healthUp;

  public Character() {
    this.name = "";
    this.description = "";
    this.characterType = CharacterType.OVERWORLD;
    this.level = 1;
    this.exp = 0;
    this.attack = 0;
    this.health = 0;
    this.mana = 0;
    this.attackUp = 0;
    this.healthUp = 0;
  }

  public Character(String name, String description, CharacterType element, double attack, double health, double mana, double attackUp, double healthUp) {
    this.name = name;
    this.description = description;
    this.characterType = element;
    this.level = 1;
    this.exp = 0;
    this.attack = attack;
    this.health = health;
    this.mana = mana;
    this.attackUp = attackUp;
    this.healthUp = healthUp;
  }

  public CharacterType getType() {
    return characterType;
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

  public double getMana() {
    return mana;
  }

  public void setMana(double mana) {
    this.mana = mana;
  }

  public double getAttackUp() {
    return attackUp;
  }

  public double getHealthUp() {
    return healthUp;
  }

  public boolean isStronger(Character enemy) {
    return ((getType() == CharacterType.OVERWORLD && enemy.getType() == CharacterType.END) || (getType() == CharacterType.END && enemy.getType() == CharacterType.NETHER) || (getType() == CharacterType.NETHER && enemy.getType() == CharacterType.OVERWORLD));
  }

  public void attackEnemy(Character enemy) {
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

  public void summon() {
    // on going
  }

  @Override
  public String toString() {
    return "Name: " + this.name + "\nDescription: " + this.description + "\nType: " + this.characterType + "\nLevel: " + this.level + "\nExp: " + this.exp;
  }
}
