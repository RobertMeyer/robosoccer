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
 *     Luis Crespo
 *     - Added getCurrentScore()
 *     Flemming N. Larsen
 *     - Ported to Java 5
 *     - Renamed method names and removed unused methods
 *     - Ordered all methods more naturally
 *     - Added methods for getting current scores
 *******************************************************************************/
package net.sf.robocode.battle.peer;

import java.util.ArrayList;
import java.util.HashMap;

import robocode.BattleResults;

/**
 * @author Mathew A. Nelson (original)
 * @author Luis Crespo (contributor)
 * @author Flemming N. Larsen (contributor)
 */
public class TeamStatistics implements ContestantStatistics {
    private final TeamPeer teamPeer;
    private int rank;

    /* ArrayList of scores for type String, ArrayList:
     * 0: total score
     * 1: current score
     */
    private HashMap<String, ArrayList<Double>> scores;
    
    /* Scores mapping to their respective unique IDs */
    private static HashMap<String, Integer> scoreIDs;
    
    static {
    	scoreIDs = new HashMap<String, Integer>();
    	/* Robocode Scores */
    	scoreIDs.put("total", 0);
    	scoreIDs.put("survival", 1);
    	scoreIDs.put("lastsurvivorbonus", 2);
    	scoreIDs.put("bulletdamage", 3);
    	scoreIDs.put("bulletkillbonus", 4);
    	scoreIDs.put("rammingdamage", 5);
    	scoreIDs.put("rammingkill", 6);
    	scoreIDs.put("firsts", 7);
    	scoreIDs.put("seconds", 8);
    	scoreIDs.put("thirds", 9);
    	
    	/* Mode-specific Scores */
    	scoreIDs.put("flag", 10);
    }
    
    /**
     * Initialise the scores to all be the sum of scores for all
     * robots in the team
     */
    private void initialiseScores() {
    	double totalScore = 0.0;
    	
    	scores = new HashMap<String, ArrayList<Double>>();
    	for (String score : scoreIDs.keySet()) {
    		scores.put(score, new ArrayList<Double>());
    		scores.get(score).add(this.getScores(false, score));
    		scores.get(score).add(this.getScores(true, score));
    	}
    }

    public TeamStatistics(TeamPeer teamPeer) {
        this.teamPeer = teamPeer;
        this.initialiseScores();
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public double getTotalScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalScore();
        }
        return d;
    }
    
    /**
     * Get a sum of the scores for a team peer, options are:
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
     * @return
     */
    public double getScores(boolean current, String ... scores) {
    	double totalScore = 0.0;
    	for (RobotPeer teammate : teamPeer) {
    		totalScore += teammate.getRobotStatistics().getScores(false, scores);
    	}
    	
    	return totalScore;
    }

    @Override
    public double getCurrentScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentScore();
        }
        return d;
    }

    @Override
    public BattleResults getFinalResults() {
        return new BattleResults(teamPeer.getName(), rank, scores);
    }

	@Override
	public void incrementScore() {	
	}
}
