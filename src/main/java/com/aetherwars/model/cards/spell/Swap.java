package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.characteristics.Swapper;
import com.aetherwars.model.cards.spell.characteristics.TemporaryEffect;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

public class Swap implements TemporaryEffect, Swapper, EffectRunner {

    private int duration;
    public Swap(){
        duration = 0;
    }

    @Override
    public void runEffect(SummonedCharacter character) {
        swap(character);
    }

    @Override
    public void removeEffect(SummonedCharacter character) {
        character.getTempSpell().remove(this);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void swap(SummonedCharacter character) {
        double tmp = character.getHealth();
        character.setHealth(character.getAttack());
        character.setAttack(tmp);
        // LOGIC FOR STACKING RULES AFTER CREATION OF TEMPORARY CARD LIST CLASS
    }
}
