package com.aetherwars.model.cards.spell;

import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.characteristics.EffectRunner;
import com.aetherwars.model.cards.spell.characteristics.HealthModifier;

public class Heal implements EffectRunner, HealthModifier {
    private double healed;
    public Heal(double healed) {
        this.healed = healed;
    }
    @Override
    public void runEffect(SummonedCharacter character) {
        modifyHealth(character);
    }

    public double getHealed() {
        return healed;
    }

    public void setHealed(double healed) {
        this.healed = healed;
    }

    @Override
    public void modifyHealth(SummonedCharacter character) {
        character.setHealthHad(character.getHealthHad() + healed);
    }
}
