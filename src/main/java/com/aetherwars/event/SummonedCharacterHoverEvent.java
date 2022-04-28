package com.aetherwars.event;

import com.aetherwars.model.cards.character.SummonedCharacter;

public class SummonedCharacterHoverEvent implements Event {
    private SummonedCharacter character;

    public SummonedCharacterHoverEvent(SummonedCharacter character) {
        this.character = character;
    }

    @Override
    public Object getEvent() {
        return this.character;
    }
}
