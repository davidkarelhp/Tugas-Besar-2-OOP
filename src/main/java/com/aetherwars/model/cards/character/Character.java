package com.aetherwars.model.cards.character;

import com.aetherwars.model.cards.Card;

import java.util.ArrayList;

public class Character extends Card {
  private CharacterType characterType;
  private double baseAttack;
  private double baseHealth;
  private double attackUp;
  private double healthUp;

  public static ArrayList<Character> characterList;

  public Character() {
    super(0, "", "", 0, "");
    this.characterType = CharacterType.OVERWORLD;
    this.baseAttack = 0;
    this.baseHealth = 0;
    this.attackUp = 0;
    this.healthUp = 0;
  }

  public Character(int id, String name, String description, int mana, String imagePath, CharacterType characterType, double baseAttack, double baseHealth, double attackUp, double healthUp) {
    super(id, name, description, mana, imagePath);
    this.characterType = characterType;
    this.baseAttack = baseAttack;
    this.baseHealth = baseHealth;
    this.attackUp = attackUp;
    this.healthUp = healthUp;
  }

  public CharacterType getCharacterType() {
    return characterType;
  }

  public double getBaseAttack() {
    return baseAttack;
  }

  public double getBaseHealth() {
    return baseHealth;
  }

  public double getAttackUp() {
    return attackUp;
  }

  public double getHealthUp() {
    return healthUp;
  }

  public int getLevel() {
    return 0;
  }

  public void levelUp() {

  }

  public void setExp(int x) {

  }

  @Override
  public void displayDesc() {
    System.out.println("Name: " + getName());
    System.out.println("Type: " + getCharacterType());
    System.out.println("Description: " + getDescription());
  };
}
