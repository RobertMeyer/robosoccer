/******************************************************************************
 * Copyright (c) 2012 The Fightin' Mongooses team.
 *
 * Contributors:
 *    Paul Wade
 *    Chris Irving
 *    Jesse Claven
******************************************************************************/

package net.sf.robocode.mode;

/**
 * A mode wherein robots can choose different weapons such as lasers.
 *
 * @author The Fightin' Moongooses
 * @deprecated This feature has temporarily been postponed.
 */
public class DifferentWeapons extends ClassicMode {

    public void execute() {
        System.out.println("Different weapons mode");
    }

    @Override
    public String toString() {
        return new String("Different weapons");
    }

    @Override
    public String getDescription() {
        return "Robots can choose a different weapon from the armoury." +
               "This mode is currently deprecated.";
    }
}
