/*
 * Copyright (C) 2009 Kees Schotanus
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

import static junit.framework.Assert.assertEquals;


/**
 * Unit tests for class {@link SequentialIdGenerator}.
 */
final class SequentialIdGeneratorTest {

    @Test
    void testGetNextId() {
        final SequentialIdGenerator zeroBased = SequentialIdGenerator.createInstance(0);
        final SequentialIdGenerator oneBased = SequentialIdGenerator.createInstance(1);

        assertEquals(0L, zeroBased.getNextId());
        assertEquals(1L, zeroBased.getNextId());
        assertEquals(1L, oneBased.getNextId());
    }

}
