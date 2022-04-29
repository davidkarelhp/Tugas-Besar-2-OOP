package com.aetherwars.model;

import com.aetherwars.AetherWars;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.Potion;
import com.aetherwars.model.cards.spell.Spell;
import com.aetherwars.model.cards.spell.Swap;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

class SpellTest extends TestCase {
    @Test
    public void swapTest() {
        Character character = new Character();
        SummonedCharacter player = new SummonedCharacter(character);

        player.setAttackSent(20);
        player.setHealthHad(30);

        Spell swapSpell = new Spell(200, "swapTest", "this for test", 20, "", new Swap(20));
        swapSpell.runEffect(player);
        assertTrue(player.getAttackSent() == 30 && player.getHealthHad() == 20);
    }

    @Test
    public void potionTest() {
        Character character = new Character();
        SummonedCharacter player = new SummonedCharacter(character);

        player.setAttackSent(20);
        player.setHealthHad(30);

        Spell potionSpell = new Spell(200, "swapTest", "this for test", 20, "", new Potion(20,30,4));
        potionSpell.runEffect(player);
        assertTrue(player.getAttackSent() == 50 && player.getHealthHad() == 50);
    }
}