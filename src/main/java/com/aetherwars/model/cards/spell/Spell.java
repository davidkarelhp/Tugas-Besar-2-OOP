package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.ArrayList;

public class Spell extends Card implements EffectRunner {
    public static ArrayList<Spell> SpellList = new ArrayList<>();
    private final SpellType type;
    private final EffectDurationType effectDurationType;

    private EffectRunner spellWorker;

    public Spell(int id, String name, String description, int mana, String imagePath, SpellType type){
        super(id, name, description, mana, imagePath);
        this.type = type;
        if (type == SpellType.LEVELDOWN) {
            spellWorker = new LevelDown();
            this.effectDurationType = EffectDurationType.PERMANENT;
        } else if (type == SpellType.MORPH){
            spellWorker = new Morph();
            this.effectDurationType = EffectDurationType.PERMANENT;
        } else if (type == SpellType.POTION){
            spellWorker = new Potion();
            this.effectDurationType = EffectDurationType.TEMPORARY;
        } else if (type == SpellType.SWAP){
            spellWorker = new Swap();
            this.effectDurationType = EffectDurationType.TEMPORARY;
        } else if (type == SpellType.LEVELUP) {
            spellWorker = new LevelUp();
            this.effectDurationType = EffectDurationType.PERMANENT;
        } else {
            this.effectDurationType = EffectDurationType.PERMANENT;
        }
    }
    public SpellType getType(){
        return type;
    }
    public EffectDurationType getEffectDurationType(){
        return effectDurationType;
    }

    public EffectRunner getSpellWorker() {
        return spellWorker;
    }

    @Override
    public void displayDesc() {
        System.out.println("Hi i am a spell!");
    }

    @Override
    public void runEffect(SummonedCharacter character) {
        spellWorker.runEffect(character);
    }
}
