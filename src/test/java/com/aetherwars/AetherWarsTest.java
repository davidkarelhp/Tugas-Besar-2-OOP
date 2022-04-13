package com.aetherwars;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class AetherWarsTest extends TestCase {
    @Test
    public void twoPlusThreeShouldEqualFive() {
        AetherWars x = new AetherWars();
        assertEquals(5, x.add(2, 3));
    }

    @Test
    public void twoMinusOneShouldEqualOne() {
        AetherWars x = new AetherWars();
        assertEquals(1, x.minus(2, 1));
    }
}