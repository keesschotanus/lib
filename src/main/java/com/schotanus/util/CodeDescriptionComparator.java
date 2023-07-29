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

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;


/**
 * Comparator for {@link CodeDescription} objects.
 * <brAllows for sorting on code and description, either ascending or descending.
 */
public class CodeDescriptionComparator<T extends Comparable<?>> implements Comparator<CodeDescription<T>> {

    /**
     * Sorts on description (true) or on code (false).
     */
    private final boolean sortOnDescription;

    /**
     * Sorts ascending (true) or descending (false).
     */
    private final boolean ascending;

    private final Collator collator;

    /**
     * Creates this comparator, sorting on description in ascending order.
     */
    public CodeDescriptionComparator() {
        this(true, true, null);
    }

    /**
     * Creates a comparator, sorting on either code or description in ascending order.
     * @param sortOnDescription True to sort on description, false to sort on code.
     */
    public CodeDescriptionComparator(final boolean sortOnDescription) {
        this(sortOnDescription, true, null);
    }

    /**
     * Creates a comparator, sorting on either code or description and either ascending or descending order.
     * @param sortOnDescription True to sort on description, false to sort on code.
     * @param ascending True to sort ascending, false to sort descending.
     */
    public CodeDescriptionComparator(final boolean sortOnDescription, final boolean ascending) {
        this(sortOnDescription, ascending, null);
    }

    /**
     * Creates a comparator, sorting on either code or description and either ascending or descending other.
     * @param sortOnDescription True to sort on description, false to sort on code.
     * @param ascending True to sort ascending, false to sort descending.
     * @param collator The collator used to compare code and description.
     *  When null, a collator using the default locale is used.
     */
    public CodeDescriptionComparator(final boolean sortOnDescription, final boolean ascending, final Collator collator) {
        this.sortOnDescription = sortOnDescription;
        this.ascending = ascending;
        this.collator = Objects.requireNonNullElse(collator, Collator.getInstance(Locale.getDefault()));
    }

    /**
     * Compares two {@link CodeDescription} objects.
     * @param left The left object.
     * @param right The right object.
     * @return -1 if the left object is before the right one,
     *  o if both objects are equal and 1 if the left object is after the second one.
     */
    @Override
    public int compare(final CodeDescription<T> left, final CodeDescription<T> right) {
        int result;
        if (sortOnDescription) {
            result = collator.compare(left.description(), right.description());
        } else {
            if (left.code() == null && right.code() == null) {
                result = 0;
            } else if (left.code() == null) {
                result = -1;
            } else if (right.code() == null) {
                result = 1;
            } else {
                Comparable<T> leftCode = (Comparable<T>) left.code();
                Comparable<T> rightCode = (Comparable<T>) right.code();
                result = leftCode.compareTo((T) rightCode);
            }
        }
        return ascending ? result : -result;
    }

    /**
     * Creates a String representation of this comparator.
     * @return A String representation of this comparator.
     */
    public String toString() {
        return String.format("Sort on: %s (%s)",
            sortOnDescription ? "description" : "code",
            ascending ? "ascending" : "descending");
    }

}
