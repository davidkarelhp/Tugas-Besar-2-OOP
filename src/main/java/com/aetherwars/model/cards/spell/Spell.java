package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.ArrayList;
import java.util.List;

public abstract class Spell {
    private final SpellType type;
    private final EffectDurationType effectDurationType;

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

    public abstract void runEffect(SummonedCharacter character);
}
