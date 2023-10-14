package com.schotanus.science.physics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * Unit tests for class {@link Phase}.
 */
class PhaseTest {

    /**
     * Tests {@link Phase.Transition#getTransition(Phase, Phase)}.
     */
    @Test
    void test() {
        assertEquals(Phase.Transition.FREEZE, Phase.Transition.getTransition(Phase.LIQUID, Phase.SOLID));
        assertNull(Phase.Transition.getTransition(Phase.SOLID, Phase.PLASMA));
    }

}
