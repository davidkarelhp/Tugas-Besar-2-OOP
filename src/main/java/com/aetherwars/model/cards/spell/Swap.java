package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.characteristics.Swapper;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;

import java.util.ArrayList;

public class Swap implements Swapper, EffectRunner {
    public final SpellType type = SpellType.SWAP;
    public final EffectDurationType effectDurationType = EffectDurationType.TEMPORARY;
    public static ArrayList<Swap> SwapList = new ArrayList<>();

    private int duration;
    public Swap(){
        duration = 0;
    }

    public Swap(int duration) {
        this.duration = duration;
    }

    @Override
    public void runEffect(SummonedCharacter character) {
        swap(character);
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void swap(SummonedCharacter character) {
        double tmp = character.getHealthHad();
        character.setHealthHad(character.getAttackSent());
        character.setAttackSent(tmp);
    }
}
