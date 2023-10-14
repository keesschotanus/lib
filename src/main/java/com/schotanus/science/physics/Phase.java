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

package com.schotanus.science.physics;

import java.util.EnumMap;
import java.util.Map;


/**
 * Enumeration of the different phases and their possible transitions.
 * <br>A Phase uses a nested EnumMap to associate data with enum pairs.
 * @author Joshua Bloch, Effective Java 2nd edition, item 33.
 */
public enum Phase {

    /**
     * Solid state.
     */
    SOLID,

    /**
     * Liquid state.
     */
    LIQUID,

    /**
     * Gas state.
     */
    GAS,

    /**
     * Plasma.
     */
    PLASMA;

    /**
     * Transition from one Phase to another.
     * @author Joshua Bloch
     * @version 1.0 $Revision: 5460 $
     */
    public enum Transition {

        /**
         * Melt.
         */
        MELT(SOLID, LIQUID),

        /**
         * Freeze.
         */
        FREEZE(LIQUID, SOLID),

        /**
         * Boil.
         */
        BOIL(LIQUID, GAS),

        /**
         * Condense.
         */
        CONDENSE(GAS, LIQUID),

        /**
         * Sublime.
         */
        SUBLIME(SOLID, GAS),

        /**
         * Deposit.
         */
        DEPOSIT(GAS, SOLID),

        /**
         * Ionize.
         */
        IONIZE(GAS, PLASMA),

        /**
         * Deionize.
         */
        DEIONIZE(PLASMA, GAS);

        /**
         * The map with state transitions from one phase to another.<br>
         * Key: Phase<br>
         * Value: Map where the key is the toPhase and the value is a transition.
         */
        private static final Map<Phase, Map<Phase, Transition>> map = new EnumMap<>(Phase.class);

        /**
         * Transition from phase.
         */
        private final Phase fromPhase;

        /**
         * Transition to phase.
         */
        private final Phase toPhase;

        /*
         * Initializes the state transition map.
         */
        static {
            // Create an empty EnumMap for each phase
            for (final Phase phase : Phase.values()) {
                map.put(phase, new EnumMap<>(Phase.class));
            }

            // For each transition, get the map by phase and store the toPhase and transition into it.
            for (final Transition transition : Transition.values()) {
                map.get(transition.fromPhase).put(transition.toPhase, transition);
            }
        }

        /**
         * Creates a transition from one phase to another.
         * @param fromPhase The phase to convert from.
         * @param toPhase The phase to convert to.
         */
        Transition(final Phase fromPhase, final Phase toPhase) {
            this.fromPhase = fromPhase;
            this.toPhase = toPhase;
        }

        /**
         * Gets the transition from one phase to another.
         * @param fromPhase The phase to transition from.
         * @param toPhase The phase to transition to.
         * @return The transition corresponding to the supplied from and to phase.
         *  <br>Null is returned when no transition is possible.
         */
        public static Transition getTransition(final Phase fromPhase, final Phase toPhase) {
            return map.get(fromPhase).get(toPhase);
        }
    }

}
