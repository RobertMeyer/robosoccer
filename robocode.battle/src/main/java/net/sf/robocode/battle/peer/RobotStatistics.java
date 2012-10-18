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
 *     - Bugfix: scoreDeath() incremented totalFirsts even if the robot was
 *       already a winner, where scoreWinner() has already been called previously
 *     - Added constructor that takes an additonal RobotResults that must be
 *       copied into this object and added the getResults() in order to support
 *       the replay feature
 *     - Changed the survivalScore and totalSurvivalScore fields to be integers
 *     - Renamed method names and removed unused methods
 *     - Ordered all methods more naturally
 *     - Added methods for getting current scores
 *     - Optimizations
 *     - Removed damage parameter from the scoreRammingDamage() method, as the
 *       damage is constant and defined by Rules.ROBOT_HIT_DAMAGE and the score
 *       of hitting a robot is defined by Rules.ROBOT_HIT_BONUS
 *     Titus Chen
 *     - Bugfix: Initial getResults() method only factored in the most recent
 *       round
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *     Nathaniel Troutman
 *     - Added cleanup() method for cleaning up references to internal classes
 *       to prevent circular references causing memory leaks
 *******************************************************************************/
package net.sf.robocode.battle.peer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import robocode.BattleResults;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.battle.BattleProperties;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Luis Crespo (contributor)
 * @author Titus Chen (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Nathaniel Troutman (contributor)
 * @author BrandonCW (rework)
 */
public class RobotStatistics implements ContestantStatistics {
	private BattleProperties bp = new BattleProperties();
	
    private final RobotPeer robotPeer;
    private int rank;
    private final int robots;
    private boolean isActive;
    private boolean isInRound;
    private Map<String, Double> robotDamageMap;
    
    /* ArrayList of scores for type String, ArrayList:
     * 0: total score
     * 1: current score
     */
    private HashMap<String, ArrayList<Double>> scores;
    
    /* Scores mapping to their respective unique IDs */
    private static ArrayList<String> scoreIDs;
    
    static {
    	scoreIDs = new ArrayList<String>();
    	/* Robocode Scores */
    	scoreIDs.add("total");
    	scoreIDs.add("survival");
    	scoreIDs.add("lastsurvivorbonus");
    	scoreIDs.add("bulletdamage");
    	scoreIDs.add("bulletkillbonus");
    	scoreIDs.add("rammingdamage");
    	scoreIDs.add("rammingkill");
    	scoreIDs.add("firsts");
    	scoreIDs.add("seconds");
    	scoreIDs.add("thirds");
    	
    	/* Mode-specific Scores */
    	scoreIDs.add("flag");
    }
    
    /**
     * Initialise the scores to all be 0.0
     */
    private void initialiseScores() {
    	scores = new HashMap<String, ArrayList<Double>>();
    	for (String key : scoreIDs) {
    		scores.put(key, new ArrayList<Double>());
    		scores.get(key).add(0.0);
    		scores.get(key).add(0.0);
    	}
    }
    
    /**
     * Initialise the scores to their respective matches
     * @param results Results containing the scores to be matched
     */
    private void initialiseScores(BattleResults results) {
    	HashMap<String, Double> resultsScores = new HashMap<String, Double>();
    	this.initialiseScores();
    	results.populateScoresMap(resultsScores);
    	
    	for (String score : scoreIDs) {
    		scores.get(score).add(1, resultsScores.get(score));
    	}
    }
    
    /**
     * Get the scores current map
     * @return
     */
    public HashMap<String, ArrayList<Double>> getScoreMap() {
    	return scores;
    }

    public RobotStatistics(RobotPeer robotPeer, int robots) {
        super();
        this.robotPeer = robotPeer;
        this.robots = robots;
        
        /* Initialise scoring */
        this.initialiseScores();
    }

    public RobotStatistics(RobotPeer robotPeer, int robots, BattleResults results) {
        this(robotPeer, robots);
        
        /* Initialise scoring */
        this.initialiseScores(results);
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    public void initialize() {
        resetScores();

        isActive = true;
        isInRound = true;
    }

    /**
     * Set all the scores to 0.0
     */
    public void resetScores() {
    	/* Robocode scores */
        robotDamageMap = null;
        for (String score : scores.keySet()) {
        	this.scores.get(score).add(1, 0.0);
        }
    }

    /**
     * Generate a map relating a Score type to it's current score
     * @param toStore Where to store the map
     */
    private void generateCurrentScoresMap(HashMap<String, Double> toStore) {
    	for (String score : scoreIDs) {
    		toStore.put(score, scores.get(score).get(1));
    	}
    }
    
    /**
     * Add the current scores to the total scores
     * @param battleProp
     */
    public void generateTotals(BattleProperties battleProp) {
    	double oldScore;
    	double toAdd;
    	for (String score : scoreIDs) {
    		oldScore = this.scores.get(score).get(0);
    		toAdd = this.scores.get(score).get(1);
    		
    		this.scores.get(score).add(0, oldScore + toAdd);
    	}
    	
        /*TODO Edit this Andrew */
        /* Set battle Properties */
        bp = battleProp;
        ClassicMode mode = (ClassicMode) bp.getBattleMode();
        this.scores.get("total").add(0, mode.getCustomOverallScore(this));
        isInRound = false;
    }
    
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
    public double getScores(boolean current, String ... scoresToGet) {
    	/* 0 for total, 1 for current */
    	int index = (current) ? 1 : 0;
    	double totalScore = 0.0;
    	
    	/* Get the score for each kind of score requested */
    	for (String score : scoresToGet) {
    		totalScore += scores.get(score).get(index);
    	}
    	
    	return totalScore;
    }
    
    /**
     * Get the total of all the current scores
     * @return Total of current scores
     */
    public double getCurrentScore() {
    	double currentScore = 0.0;
    	for (String score : scores.keySet()) {
    		currentScore += scores.get(score).get(1);
    	}
    	
    	return currentScore;
    }
    
    /**
     * Get the total of all the total scores
     * @return Total of total scores
     */
    public double getTotalScore() {
    	double totalScore = 0.0;
    	for (String score : scores.keySet()) {
    		totalScore += scores.get(score).get(0);
    	}
    	
    	return totalScore;
    }
    
    /**
     * Score a specific kind of score
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
     * @param current true for current, false for total
     * @param score The kind of score to score
     * @param points How many points to score
     */
    private void scoreSpecific(boolean current, String score, double points) {
    	double oldScore = this.scores.get(score).get(1);
    	double toAdd = points;
    	
    	this.scores.get(score).add(1, oldScore + toAdd);
    }
    
    public void scoreSurvival() {
        if (isActive) {
        	scoreSpecific(true, "survival", 50);
        }
    }

    public void scoreLastSurvivor() {
        if (isActive) {
            int enemyCount = robots - 1;

            if (robotPeer.getTeamPeer() != null) {
                enemyCount -= (robotPeer.getTeamPeer().size() - 1);
            }
            this.scoreSpecific(true, "lastsurvivorbonus", 10 * enemyCount);

            if (robotPeer.getTeamPeer() == null || robotPeer.isTeamLeader()) {
                this.scoreSpecific(true, "totalFirsts", 1);
            }
        }
    }

    public void scoreBulletDamage(String robot, double damage) {
        if (isActive) {
            incrementRobotDamage(robot, damage);
            this.scoreSpecific(true, "bulletdamage", damage);
        }
    }

    public double scoreBulletKill(String robot) {
        if (isActive) {
            double bonus;

            if (robotPeer.getTeamPeer() == null) {
                bonus = getRobotDamage(robot) * 0.20;
            } else {
                bonus = 0;
                for (RobotPeer teammate : robotPeer.getTeamPeer()) {
                    bonus += teammate.getRobotStatistics().getRobotDamage(robot) * 0.20;
                }
            }

            this.scoreSpecific(true, "bulletkillbonus", bonus);
            return bonus;
        }
        return 0;
    }

    /* TODO Score rework */
    public void scoreRammingDamage(String robot) {
        if (isActive) {
            incrementRobotDamage(robot, robocode.Rules.ROBOT_HIT_DAMAGE);
            this.scoreSpecific(true, "rammingdamage", robotPeer.getRamAttack());
        }
    }

    public double scoreRammingKill(String robot) {
        if (isActive) {
            double bonus;

            if (robotPeer.getTeamPeer() == null) {
                bonus = getRobotDamage(robot) * 0.30;
            } else {
                bonus = 0;
                for (RobotPeer teammate : robotPeer.getTeamPeer()) {
                    bonus += teammate.getRobotStatistics().getRobotDamage(robot) * 0.30;
                }
            }
            this.scoreSpecific(true, "rammingkill", bonus);
            return bonus;
        }
        return 0;
    }

    public void scoreRobotDeath(int enemiesRemaining, Boolean botzillaActive) {
    	if (botzillaActive) {
    		enemiesRemaining--;
    	}
        switch (enemiesRemaining) {
            case 0:
                if (!robotPeer.isWinner()) {
                    this.scoreSpecific(true, "firsts", 1);
                }
                break;

            case 1:
                this.scoreSpecific(true, "seconds", 1);
                break;

            case 2:
                this.scoreSpecific(true, "thirds", 1);
                break;
        }
    }

    /**
     * Score the flag points
     */
    public void scoreFlag() {
        this.scoreSpecific(true, "flag", 1);
    }

    public void scoreFirsts() {
        if (isActive) {
            this.scoreSpecific(true, "firsts", 1);
        }
    }
    
    /* TODO Scoring rework */
    /**
     * Circumventing the fancy cumulative score. This is for Dispenser's sake,
     * who scores with a functionality not shared with other robots.
     * @param robot The robot's name - preferably getName() from a Dispenser Bot
     */
    public void incrementTotalScore(String robot) {
        if (isActive) {
        	this.scoreSpecific(false, "total", 1);
        }
    }

    public void setInactive() {
        resetScores();
        isActive = false;
    }

    /* TODO Scoring rework */
    @Override
    public BattleResults getFinalResults() {
    	HashMap<String, Double> currentScores = new HashMap<String, Double>();
    	this.generateCurrentScoresMap(currentScores);
    	
        return new BattleResults(robotPeer.getTeamName(), rank, currentScores);
    }

    private double getRobotDamage(String robot) {
        if (robotDamageMap == null) {
            robotDamageMap = new HashMap<String, Double>();
        }
        Double damage = robotDamageMap.get(robot);

        return (damage != null) ? damage : 0;
    }

    private void incrementRobotDamage(String robot, double damage) {
        double newDamage = getRobotDamage(robot) + damage;

        robotDamageMap.put(robot, newDamage);
    }

    public void cleanup() {// Do nothing, for now
    }

    public boolean isInRound() {
        return isInRound;
    }

	@Override
	public void incrementScore() {	
	}
}
