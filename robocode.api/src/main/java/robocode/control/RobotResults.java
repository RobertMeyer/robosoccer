/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Changed to be consistent with the battle results and ranking scores
 *     - This class now implements java.io.Serializable
 *     - Updated Javadocs
 *******************************************************************************/
package robocode.control;

import java.util.ArrayList;
import java.util.HashMap;

import robocode.BattleResults;

/**
 * Contains the battle results for an individual robot
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class RobotResults extends BattleResults {

    private static final long serialVersionUID = 2L;
    private final RobotSpecification robot;

    /**
     * Constructs new RobotResults.
     *
     * @param robot			 the robot these results are for
     * @param teamLeaderName	team name
     * @param rank			  the rank of the robot in the battle
     * @param scores HashMap of scores, total at 0, current at 1
     */
    public RobotResults(
            RobotSpecification robot,
            String teamLeaderName,
            int rank,
            HashMap<String, ArrayList<Double>> scores) {
        super(teamLeaderName, rank, scores);
        this.robot = robot;
    }

    /**
     * Constructs new RobotResults based on a {@link RobotSpecification} and {@link robocode.BattleResults
     * BattleResults}.
     *
     * @param robot   the robot these results are for
     * @param results the battle results for the robot
     */
    public RobotResults(
            RobotSpecification robot,
            BattleResults results) {
        super(results.getTeamLeaderName(), results.getRank(), results.getScoreMap());
        this.robot = robot;
    }

    /**
     * Returns the robot these results are meant for.
     *
     * @return the robot these results are meant for.
     */
    public RobotSpecification getRobot() {
        return robot;
    }

    /**
     * Converts an array of {@link BattleResults} into an array of {@link RobotResults}.
     *
     * @param results an array of BattleResults to convert.
     * @return an array of RobotResults converted from BattleResults.
     * @since 1.6.2
     */
    public static RobotResults[] convertResults(BattleResults[] results) {
        RobotResults[] resultsConv = new RobotResults[results.length];

        for (int i = 0; i < results.length; i++) {
            resultsConv[i] = (RobotResults) results[i];
        }
        return resultsConv;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;

        /* For each score */
        for (String score : scores.keySet()) {
        	if (score.equals("firsts") || score.equals("seconds") || score.equals("thirds")) {
        		continue;
        	}
        	
        	temp = Double.doubleToLongBits(scores.get(score).get(1));
        	result = prime * result + (int) (temp ^ (temp >>> 32));
        }
        
        /* Other values */
        result = prime * result + rank;
        result = (int) (prime * result + scores.get("firsts").get(1));
        result = (int) (prime * result + scores.get("seconds").get(1));
        result = (int) (prime * result + scores.get("thirds").get(1));
        result = prime * result + ((teamLeaderName == null) ? 0 : teamLeaderName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    	double original;
    	double toCheck;
    	
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RobotResults other = (RobotResults) obj;

        for (String score : scores.keySet()) {
        	original = Double.doubleToLongBits(scores.get(score).get(1));
        	toCheck = Double.doubleToLongBits(other.getScores(true, score));
        	if (original != toCheck) {
        		return false;
        	}
        }
        
        
        if (rank != other.rank) {
            return false;
        }
   
        if (teamLeaderName == null) {
            if (other.teamLeaderName != null) {
                return false;
            }
        } else if (!teamLeaderName.equals(other.teamLeaderName)) {
            return false;
        }

        return true;
    }
}
