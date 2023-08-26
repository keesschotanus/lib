/*
 * Copyright (C) 2010 Kees Schotanus
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.schotanus.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Unit tests for class {@link RpnCalc}.
 */
class RpnCalcTest {

    /**
     * The RPN calculator.
     */
    private final RpnCalc calculator = new RpnCalc();

    @Test
    void testEvaluate() {
        assertEquals(2.0D, calculator.evaluate("3 4 - 5 + sqrt"), 1.0e-10);

        // The previous test left the result on the stack, so clear the stack
        calculator.evaluate("ac");
        // Test getting exception when not enough operands on the stack
        assertThrows(IllegalStateException.class, () -> calculator.evaluate("sin"));
        assertThrows(IllegalStateException.class, () -> calculator.evaluate("+"));
    }

    /**
     * Nothing much we can test here since we don;t have a console
     */
    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> RpnCalc.main(new String[]{"2 3 4 * +"}));
    }
}
