package com.aetherwars.model.cards.spell.characteristics;

import com.aetherwars.model.cards.character.SummonedCharacter;

public interface HealthModifier {
    void modifyHealth(SummonedCharacter character);
}
