/*
 *
 * Copyright (C) 2008 Kees Schotanus
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

import java.util.concurrent.atomic.AtomicLong;


/**
 * Generates sequential id's that are unique within the lifetime of an instance of this class.
 */
public final class SequentialIdGenerator {

    /**
     * The next id.
     */
    private final AtomicLong nextId;

    /**
     * Private constructor prevents creation of an instance outside this class.
     * @param start The value at which this generator should start.
     */
    private SequentialIdGenerator(final long start) {
        this.nextId = new AtomicLong(start);
    }

    /**
     * Creates a sequential id generator that will count up from (and including) the supplied start value.
     * @param start The value at which this generator should start.
     * @return A sequential id generator which will start at the supplied start value.
     */
    public static SequentialIdGenerator createInstance(final long start)  {
        return new SequentialIdGenerator(start);
    }

    /**
     * Gets the next sequential id.
     * @return The next sequential id.
     */
    public long getNextId() {
        return this.nextId.getAndIncrement();
    }
}
