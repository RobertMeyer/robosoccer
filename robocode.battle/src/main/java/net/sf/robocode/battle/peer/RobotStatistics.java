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
 */
public class RobotStatistics implements ContestantStatistics {
	private BattleProperties bp = new BattleProperties();
    private final RobotPeer robotPeer;
    private int rank;
    private final int robots;
    private boolean isActive;
    private boolean isInRound;
    private double survivalScore;
    private double lastSurvivorBonus;
    private double bulletDamageScore;
    private double bulletKillBonus;
    private double rammingDamageScore;
    private double rammingKillBonus;
    // Team-Telos addition
    private double flagScore;
    private int kills;
    private Map<String, Double> robotDamageMap;
    private double totalScore;
    private double totalSurvivalScore;
    private double totalLastSurvivorBonus;
    private double totalBulletDamageScore;
    private double totalBulletKillBonus;
    private double totalRammingDamageScore;
    private double totalRammingKillBonus;
    // Team-Telos addition
    private double totalFlagScore;
    private int totalFirsts;
    private int totalSeconds;
    private int totalThirds;

    private int totalKills;
    
    //RaceMode Addition
    private double raceScore;
    private double totalRaceScore;

    public RobotStatistics(RobotPeer robotPeer, int robots) {
        super();
        this.robotPeer = robotPeer;
        this.robots = robots;
    }

    public RobotStatistics(RobotPeer robotPeer, int robots, BattleResults results) {
        this(robotPeer, robots);

        totalScore = results.getScore();
        totalSurvivalScore = results.getSurvival();
        totalLastSurvivorBonus = results.getLastSurvivorBonus();
        totalBulletDamageScore = results.getBulletDamage();
        totalBulletKillBonus = results.getBulletDamageBonus();
        totalRammingDamageScore = results.getRamDamage();
        totalRammingKillBonus = results.getRamDamageBonus();
        totalFlagScore = results.getFlagScore();
        totalFirsts = results.getFirsts();
        totalSeconds = results.getSeconds();
        totalThirds = results.getThirds();
        totalKills = results.getKills();
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

    public void resetScores() {
        robotDamageMap = null;
        survivalScore = 0;
        lastSurvivorBonus = 0;
        bulletDamageScore = 0;
        bulletKillBonus = 0;
        rammingDamageScore = 0;
        rammingKillBonus = 0;
        flagScore = 0;
        kills = 0;
        raceScore = 0;
    }

    public void generateTotals(BattleProperties battleProp) {
        totalSurvivalScore += survivalScore;
        totalLastSurvivorBonus += lastSurvivorBonus;
        totalBulletDamageScore += bulletDamageScore;
        totalBulletKillBonus += bulletKillBonus;
        totalRammingDamageScore += rammingDamageScore;
        totalRammingKillBonus += rammingKillBonus;
        // team-Telos addition
        totalFlagScore += flagScore;
        totalKills += kills;

        /* Set battle Properties */
        bp = battleProp;
        ClassicMode mode = (ClassicMode) bp.getBattleMode();
        totalScore = mode.getCustomOverallScore(this);
        
        totalRaceScore += raceScore;

        if(robotPeer.getCurrentWaypointIndex() > 0 ){
        	totalScore = totalRaceScore;
        }else{
        	totalScore = totalBulletDamageScore + totalRammingDamageScore + totalSurvivalScore + totalRammingKillBonus
                + totalBulletKillBonus + totalLastSurvivorBonus + totalFlagScore + totalRaceScore;
        }
        isInRound = false;
    }
    
    
    /**
     * Begin Team Telos custom overall score additions
     */
    
    public double showBulletDamageScore() {
    	return totalBulletDamageScore;
    }
    
    public double showRammingDamageScore() {
    	return totalRammingDamageScore;
    }
    
    public double showSurvivalScore() {
    	return totalSurvivalScore;
    }
    
    public double showRammingKillBonus() {
    	return totalRammingKillBonus;
    }
    
    public double showBulletKillBonus() {
    	return totalBulletKillBonus;
    }
    
    public double showLastSurvivorBonus() {
    	return totalLastSurvivorBonus;
    }
    
    public double showFlagScore() {
    	return totalFlagScore;
    }

    public int showKills() {
    	return totalKills;
    }
    
    /**
     * End Team Telos custom overall score additions
     */
    
    @Override
    public double getTotalScore() {
        return totalScore;
    }

    @Override
    public double getTotalSurvivalScore() {
        return totalSurvivalScore;
    }

    @Override
    public double getTotalLastSurvivorBonus() {
        return totalLastSurvivorBonus;
    }

    @Override
    public double getTotalBulletDamageScore() {
        return totalBulletDamageScore;
    }

    @Override
    public double getTotalBulletKillBonus() {
        return totalBulletKillBonus;
    }

    @Override
    public double getTotalRammingDamageScore() {
        return totalRammingDamageScore;
    }

    @Override
    public double getTotalRammingKillBonus() {
        return totalRammingKillBonus;
    }

    public double getTotalFlagScore() {
        // FIXME - team-Telos
        return totalFlagScore;
    }
    
    public double getTotalRaceScore(){
    	return totalRaceScore;
    }

    @Override
    public int getTotalFirsts() {
        return totalFirsts;
    }

    @Override
    public int getTotalSeconds() {
        return totalSeconds;
    }

    @Override
    public int getTotalThirds() {
        return totalThirds;
    }
    
    @Override
    public int getTotalKills() {
    	return totalKills;
    }

    @Override
    public double getCurrentScore() {
        return bulletDamageScore + rammingDamageScore + survivalScore + rammingKillBonus + bulletKillBonus
                + lastSurvivorBonus + raceScore;
    }

    @Override
    public double getCurrentSurvivalScore() {
        return survivalScore;
    }

    @Override
    public double getCurrentSurvivalBonus() {
        return lastSurvivorBonus;
    }

    @Override
    public double getCurrentBulletDamageScore() {
        return bulletDamageScore;
    }

    @Override
    public double getCurrentBulletKillBonus() {
        return bulletKillBonus;
    }

    @Override
    public double getCurrentRammingDamageScore() {
        return rammingDamageScore;
    }

    @Override
    public double getCurrentRammingKillBonus() {
        return rammingKillBonus;
    }
    
	@Override    
    public double getCurrentFlagScore() {
    	return flagScore;
    }

	@Override
	public int getCurrentKills() {
		return kills;
	}
	public double getCurrentRaceScore() {
		return raceScore;
	}
    
    public void scoreSurvival() {
        if (isActive) {
            survivalScore += 50;
        }
    }

    public void scoreLastSurvivor() {
        if (isActive) {
            int enemyCount = robots - 1;

            if (robotPeer.getTeamPeer() != null) {
                enemyCount -= (robotPeer.getTeamPeer().size() - 1);
            }
            lastSurvivorBonus += 10 * enemyCount;

            if (robotPeer.getTeamPeer() == null || robotPeer.isTeamLeader()) {
                totalFirsts++;
            }
        }
    }

    public void scoreBulletDamage(String robot, double damage) {
        if (isActive) {
            incrementRobotDamage(robot, damage);
            bulletDamageScore += damage;
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

            bulletKillBonus += bonus;
            return bonus;
        }
        return 0;
    }

    public void scoreRammingDamage(String robot) {
        if (isActive) {
        	if(Math.abs(robotPeer.getRamDamage() - 1.0) < 0.00001)
        		incrementRobotDamage(robot, robocode.Rules.ROBOT_HIT_DAMAGE);
        	else incrementRobotDamage(robot, robotPeer.getRamDamage());
            rammingDamageScore += robotPeer.getRamAttack();
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
            rammingKillBonus += bonus;
            return bonus;
        }
        return 0;
    }

    public void scoreRobotDeath(int enemiesRemaining, Boolean botzillaActive) {
    	//If botzilla is in the match don't count him as an enemy remaining for
    	//the purposes of scoring
    	if (botzillaActive) {
    		enemiesRemaining--;
    	}
        switch (enemiesRemaining) {
            case 0:
                if (!robotPeer.isWinner()) {
                    totalFirsts++;
                }
                break;

            case 1:
                totalSeconds++;
                break;

            case 2:
                totalThirds++;
                break;
        }
    }

    /**
     * Team-Telos - Score the flag points
     */
    public void scoreFlag() {
        flagScore++;
    }

    public void scoreRace(){
    	raceScore++;
    }
    
    public void scoreFirsts() {
        if (isActive) {
            totalFirsts++;
        }
    }
    
    /**
     * Circumventing the fancy cumulative score. This is for Dispenser's sake,
     * who scores with a functionality not shared with other robots.
     * @param robot The robot's name - preferably getName() from a Dispenser Bot
     */
    public void incrementTotalScore(String robot) {
        if (isActive) {
            totalScore++;
        }
    }

    public void setInactive() {
        resetScores();
        isActive = false;
    }

    @Override
    public BattleResults getFinalResults() {
        return new BattleResults(robotPeer.getTeamName(), rank, totalScore, totalSurvivalScore, totalLastSurvivorBonus,
                                 totalBulletDamageScore, totalBulletKillBonus, totalRammingDamageScore, totalRammingKillBonus,
                                 totalFlagScore, totalRaceScore, totalFirsts, totalSeconds, totalThirds, totalKills);

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

	public void scoreKill() {
		kills++;
	}
}
