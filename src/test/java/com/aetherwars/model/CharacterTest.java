package com.aetherwars.model;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.CharacterType;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

class CharacterTest extends TestCase {
    @Test
    public void levelUpTest1() {
        Character player = new Character("Creeper", "", CharacterType.OVERWORLD, 10, 2, 4, 1, 1);
        player.addExp(2);
        assertTrue(player.getLevel() == 2 && player.getExp() == 1);
    }

    @Test
    public void levelUpTest2() {
        Character player = new Character("Creeper", "", CharacterType.OVERWORLD, 10, 2, 4, 1, 1);
        player.addExp(6);
        assertTrue(player.getLevel() == 3 && player.getExp() == 2);
    }
}