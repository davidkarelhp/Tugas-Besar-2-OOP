package com.aetherwars.model.cards.spell.characteristics;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;

public interface TemporaryEffect {
    void removeEffect(SummonedCharacter character);
}
