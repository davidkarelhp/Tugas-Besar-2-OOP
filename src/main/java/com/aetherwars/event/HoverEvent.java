package com.aetherwars.event;

import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.SummonedCharacter;

public class HoverEvent implements Event {

    Card card;

    public HoverEvent(Card card){

        this.card = card;
    }

    @Override
    public Object getEvent(){
        return this.card;
    }

}
