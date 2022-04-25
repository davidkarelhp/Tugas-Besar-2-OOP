package com.aetherwars.model.cards.spell;

//import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

public abstract class Spell {
    private SpellType type;
    private EffectDurationType effectDurationType;

    public Spell(SpellType type, EffectDurationType effectDurationType){
        this.type = type;
        this.effectDurationType = effectDurationType;
    }
    public SpellType getType(){
        return type;
    }
    public EffectDurationType getEffectDurationType(){
        return effectDurationType;
    }

    public abstract void runEffect(Character character);
}
