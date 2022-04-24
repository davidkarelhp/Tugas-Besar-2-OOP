package com.aetherwars.model.cards;

public abstract class Card {
    private int id;
    private String name;
    private String description;
    private int mana;
    private String imagePath;

    public Card(int id, String name, String description, int mana, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mana = mana;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void displayDesc();
}
