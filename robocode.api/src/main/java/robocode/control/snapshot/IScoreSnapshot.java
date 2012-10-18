/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *     Flemming N. Larsen
 *     - Javadocs
 *******************************************************************************/
package robocode.control.snapshot;

/**
 * Interface of a score snapshot at a specific time in a battle.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 *
 * @since 1.6.2
 */
public interface IScoreSnapshot extends Comparable<Object> {

    /**
     * Returns the name of the contestant, i.e. a robot or team.
     *
     * @return the name of the contestant, i.e. a robot or team.
     */
    String getName();

    /**
     * Get a sum of the scores, options are:
     * 'total'
     * 'survival'
     * 'lastsurvivorbonus'
     * 'bulletdamage'
     * 'bulletkillbonus'
     * 'rammingdamage'
     * 'rammingkill'
     * 'firsts'
     * 'seconds'
     * 'thirds'
     * 'flag'
     * @param current true for current scores or false for total
     * @param scores The string representation of the score
     * @return The sum of the scores 'scores', current or total
     */
    public double getScores(boolean current, String ... scoresToGet);
    
    /**
     * Get the total score
     * @return Total score of the robot
     */
    public double getTotalScore();
    
    /**
     * Get the current score
     * @return Current score of the robot
     */
    public double getCurrentScore();
}
