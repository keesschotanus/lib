/*
 * Copyright (C) 2001 Kees Schotanus
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.schotanus.util;


import java.util.Objects;


/**
 * Abstraction of an immutable code and description pair.
 * <br>A collection of these code and description pairs could be used to fill a combo box for example.
 * @param <T> The type of the code.
 * @param code Code.
 * @param description Description field.
 */
public record CodeDescription<T extends Comparable<?>>(T code, String description) {

    /**
     * Constructs a code and description pair from the supplied code and description.
     *
     * @param code The code.
     * @param description The description.
     * @throws NullPointerException When the supplied description is null.
     */
    public CodeDescription(final T code, final String description) {
        this.code = code;
        this.description = Objects.requireNonNull(description);
    }

    /**
     * Gets a String representation of this code description pair.
     * <br>Since this method could be used from within a Swing component it simply returns the description.
     *
     * @return Description for this code description pair.
     */
    public String toString() {
        return description;
    }
}
