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

import robocode.BattleResults;

/**
 * @author Mathew A. Nelson (original)
 * @author Luis Crespo (contributor)
 * @author Flemming N. Larsen (contributor)
 */
public class TeamStatistics implements ContestantStatistics {

    private final TeamPeer teamPeer;
    private int rank;

    public TeamStatistics(TeamPeer teamPeer) {
        this.teamPeer = teamPeer;
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

    @Override
    public double getTotalSurvivalScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalSurvivalScore();
        }
        return d;
    }

    @Override
    public double getTotalLastSurvivorBonus() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalLastSurvivorBonus();
        }
        return d;
    }

    @Override
    public double getTotalBulletDamageScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalBulletDamageScore();
        }
        return d;
    }

    @Override
    public double getTotalBulletKillBonus() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalBulletKillBonus();
        }
        return d;
    }

    @Override
    public double getTotalRammingDamageScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalRammingDamageScore();
        }
        return d;
    }

    @Override
    public double getTotalRammingKillBonus() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalRammingKillBonus();
        }
        return d;
    }

    /**
     * Team-Telos addition
     *
     * @return
     */
    public double getTotalFlagScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalFlagScore();
        }
        return d;
    }

    @Override
    public int getTotalFirsts() {
        int d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalFirsts();
        }
        return d;
    }

    @Override
    public int getTotalSeconds() {
        int d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalSeconds();
        }
        return d;
    }

    @Override
    public int getTotalThirds() {
        int d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getTotalThirds();
        }
        return d;
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
    public double getCurrentSurvivalScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentSurvivalScore();
        }
        return d;
    }

    @Override
    public double getCurrentSurvivalBonus() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentSurvivalBonus();
        }
        return d;
    }

    @Override
    public double getCurrentBulletDamageScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentBulletDamageScore();
        }
        return d;
    }

    @Override
    public double getCurrentBulletKillBonus() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentBulletKillBonus();
        }
        return d;
    }

    @Override
    public double getCurrentRammingDamageScore() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentRammingDamageScore();
        }
        return d;
    }

    public double getCurrentFlagScore() {
    	double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentFlagScore();
        }
        return d;
    }
    
    public double getCurrentRammingKillBonus() {
        double d = 0;

        for (RobotPeer teammate : teamPeer) {
            d += teammate.getRobotStatistics().getCurrentRammingKillBonus();
        }
        return d;
    }

    @Override
    public BattleResults getFinalResults() {
        return new BattleResults(teamPeer.getName(), rank, getTotalScore(), getTotalSurvivalScore(),
                                 getTotalLastSurvivorBonus(), getTotalBulletDamageScore(), getTotalBulletKillBonus(),
                                 getTotalRammingDamageScore(), getTotalRammingKillBonus(), getTotalFlagScore(), getTotalFirsts(), getTotalSeconds(),
                                 getTotalThirds());
    }

	@Override
	public void incrementScore() {	
	}
}
