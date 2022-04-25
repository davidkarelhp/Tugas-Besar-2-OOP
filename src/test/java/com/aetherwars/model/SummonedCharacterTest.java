package com.aetherwars.model;

import com.aetherwars.AetherWars;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

class SummonedCharacterTest extends TestCase {
    @Test
    public void levelUpTest1() {
        Character character = new Character();
        SummonedCharacter player = new SummonedCharacter(character);
        player.addExp(2);
        assertTrue(player.getLevel() == 2 && player.getExp() == 1);
    }

    @Test
    public void levelUpTest2() {
        Character character = new Character();
        SummonedCharacter player = new SummonedCharacter(character);
        player.addExp(6);
        assertTrue(player.getLevel() == 3 && player.getExp() == 2);
    }
}