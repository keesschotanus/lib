/*
 * Copyright (C) 2014 Kees Schotanus
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

package com.schotanus.finance;

import com.schotanus.util.Iso3166Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * Unit tests for clas {@link IbanOfCountry}.
 */
class IbanOfCountryTest {

    @Test
    final void testGetCountry() {
        assertEquals(Iso3166Country.BELGIUM, IbanOfCountry.BELGIUM.getCountry());
    }

    @Test
    final void testGetLength() {
        assertEquals(18, IbanOfCountry.NETHERLANDS.getLength(), "Length must be 18 in The Netherlands");
    }

    @Test
    final void testGetExample() {
        assertEquals("NL91ABNA0417164300", IbanOfCountry.NETHERLANDS.getExample(), "Example incorrect");
    }

    @Test
    final void testValueOf() {
        assertEquals(IbanOfCountry.BELGIUM, IbanOfCountry.valueOf(Iso3166Country.BELGIUM));

        // Test with country that does not exist anymore
        assertNull(IbanOfCountry.valueOf(Iso3166Country.GERMANY_DEMOCRATIC_REPUBLIC));
    }

}
